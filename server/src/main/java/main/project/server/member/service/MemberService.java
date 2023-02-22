package main.project.server.member.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import main.project.server.email.event.MemberRegistrationApplicationEvent;
import main.project.server.exception.BusinessException;
import main.project.server.exception.ExceptionCode;
import main.project.server.security.jwt.service.JwtTokenizer;
import main.project.server.member.entity.Member;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberStatus;
import main.project.server.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final RedisTemplate redisTemplate;
    private final JwtTokenizer jwtTokenizer;
    @Value("${images.member-profile}")
    private String uploadPath;
    @Value("${images.upload-ec2}")
    private String uploadEc2;
    private final ApplicationEventPublisher publisher;


    public Optional<Member> findMember(String memberId) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember;
    }


    public Member createMember(Member member, MultipartFile memberImageFile) {

        // member save
        if(member.getMemberNationality().equals("LOCAL")) member.setMemberNationality(MemberNationality.LOCAL);
        else if(member.getMemberNationality().equals("FOREIGN")) member.setMemberNationality(MemberNationality.FOREIGN);

        member.setMemberStatus(MemberStatus.MEMBER_ENABLE);

        // 파일이 있는 경우만 저장
        if(!(memberImageFile.isEmpty())) member.setMemberImageUrl(saveFile(memberImageFile, member.getMemberId()));

        // 멤버 닉네임 체크
        if(checkNickName(member.getMemberNickname()) == false) {
            throw new BusinessException(ExceptionCode.NICKNAME_DUPLICATED);
        }

        Member savedMember = memberRepository.save(member);

        //매개변수로 Event를 보내는 것 자체가 이벤트 발행
        publisher.publishEvent(new MemberRegistrationApplicationEvent(this, savedMember));

        return savedMember;
    }


    public Member patchMember(Member member, MultipartFile memberImageFile) {
        Member patchMember = findVerifiedMember(member.getMemberId());

        if(member.getMemberNickname() != null) patchMember.setMemberNickname(member.getMemberNickname());

        if(!(memberImageFile.isEmpty())) {
            if(!(patchMember.getMemberImageUrl().isEmpty())) {
                deleteFile(patchMember.getMemberImageUrl());
            }
            patchMember.setMemberImageUrl(saveFile(memberImageFile, member.getMemberId()));
        }

        if(member.getMemberTags() != null) patchMember.setMemberTags(member.getMemberTags());


        return patchMember;
    }


    public Member deleteMember(String memberId) {
        Member findMember = findVerifiedMember(memberId);
        findMember.setMemberStatus(MemberStatus.MEMBER_DISABLE);

        return memberRepository.save(findMember);
    }

    public Member findVerifiedMember(String memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }



    public void registerLogoutToken(String jws) {

        ValueOperations valueOperations = redisTemplate.opsForValue();
        Jws<Claims> jwsClaims = jwtTokenizer.getClaims(
                jws,
                jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey())
        );

        Map<String, Object> claims = jwsClaims.getBody();

        String memberId = (String)claims.get("memberId");
        String logoutKey = "logout@" + jws;
        valueOperations.set(logoutKey, memberId, Duration.ofMinutes(jwtTokenizer.getAccessTokenExpirationMinutes()));
    }

    // 파일 업로드하고 저장위치 반환
    public String saveFile(MultipartFile memberImageFile, String memberId){

        String originalName = memberImageFile.getOriginalFilename();
        String fileName = StringUtils.cleanPath(originalName); //각 운영체제별 상이한 구분자를 일반화 하기 위함
        String folderPath = makeFolder(memberId);
        String saveName = File.separator + uploadPath + folderPath + File.separator + fileName;
        String savePath = uploadEc2 + saveName;
        // 경로 정의
        Path path = Paths.get(savePath).toAbsolutePath();
        try {
            memberImageFile.transferTo(path);
        } catch (IOException e) {
            throw new BusinessException(ExceptionCode.FILE_NOT_SAVED);
        }

        return saveName;
    }


    // 업로드한 파일을 저장할 폴더 생성
    public String makeFolder(String memberId){

        /* 만약 Data 밑에 exam.jpg라는 파일을 원한다고 할때,
        윈도우는 "Data\\"eaxm.jpg", 리눅스는 "Data/exam.jpg"
        자바에서는 "Data" +File.separator + "exam.jpg" */
        String folderPath = String.valueOf(memberId);

        //make folder, File newFile= new File(dir,"파일명");
        File uploadPathFolder = new File( uploadEc2 + File.separator + uploadPath, folderPath);

        //만약 uploadPathFolder가 존재하지않는다면 makeDirectory
        if(uploadPathFolder.exists() == false){

            //mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우, 생성이 불가능
            //mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우, 상위 디렉토리까지 모두 생성
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

    // 파일 삭제
    public void deleteFile(String fileName){
        String pathName = uploadEc2 + fileName;
        File file = new File(pathName);
        file.delete();
    }

    // 멤버 닉네임 중복 체크
    public boolean checkNickName(String memberNickname){

        return memberRepository.findByMemberNicknameEquals(memberNickname).isEmpty();
    }
}
