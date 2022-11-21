package main.project.server.member.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import main.project.server.jwt.JwtTokenizer;
import main.project.server.member.entity.Member;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberStatus;
import main.project.server.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;
import java.util.NoSuchElementException;
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


    public Optional<Member> findMember(String memberId) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember;
    }


    public Member createMember(Member member, MultipartFile memberImageFile) {

        // member save
        if(member.getMemberNationality().equals("LOCAL")) member.setMemberNationality(MemberNationality.LOCAL);
        else if(member.getMemberNationality().equals("FOREIGN")) member.setMemberNationality(MemberNationality.FOREIGN);
//        else throw

        member.setMemberStatus(MemberStatus.MEMBER_ENABLE);
        member.setMemberImageUrl(saveFile(memberImageFile, member.getMemberId()));

        return memberRepository.save(member);
    }


    public Member patchMember(Member member, MultipartFile memberImageFile) {
        Member patchMember = findVerifiedMember(member.getMemberId());

//        // patch 항목 고려 필요(Dto)
        if(member.getMemberNickname() != null) patchMember.setMemberNickname(member.getMemberNickname());
//        if(member.getMemberEmail() != null) patchMember.setMemberEmail(member.getMemberEmail());
//        if(member.getMemberPhone() != null) patchMember.setMemberPhone(member.getMemberPhone());
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
                optionalMember.orElseThrow(() -> new NoSuchElementException("No value present"));
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
        String fileName = originalName.substring(originalName.lastIndexOf("."));
        String folderPath = makeFolder(memberId);
        String saveName = uploadPath + folderPath + File.separator + memberId + fileName;

        // 경로 정의
        Path path = Paths.get(saveName).toAbsolutePath();
        try {
            memberImageFile.transferTo(path);
        } catch (IOException e) {
            // throw
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
        File uploadPathFolder = new File(uploadPath, folderPath);

        //만약 uploadPathFolder가 존재하지않는다면 makeDirectory
        if(uploadPathFolder.exists() == false){

            //mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우, 생성이 불가능
            //mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우, 상위 디렉토리까지 모두 생성
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

    // 파일 삭제
    public void deleteFile(String pathName){

        File file = new File(pathName);
        boolean result = file.delete();
//        if(result == false)
//        try {
//            Files.delete(Paths.get(pathName));
//        } catch (IOException e) {
//            throw
//        }
    }
}
