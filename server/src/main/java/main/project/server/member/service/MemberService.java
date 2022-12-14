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

        // ????????? ?????? ????????? ??????
        if(!(memberImageFile.isEmpty())) member.setMemberImageUrl(saveFile(memberImageFile, member.getMemberId()));

        // ?????? ????????? ??????
        if(checkNickName(member.getMemberNickname()) == false) {
            throw new BusinessException(ExceptionCode.NICKNAME_DUPLICATED);
        }

        Member savedMember = memberRepository.save(member);

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

    // ?????? ??????????????? ???????????? ??????
    public String saveFile(MultipartFile memberImageFile, String memberId){

        String originalName = memberImageFile.getOriginalFilename();
        String fileName = StringUtils.cleanPath(originalName);
        String folderPath = makeFolder(memberId);
        String saveName = File.separator + uploadPath + folderPath + File.separator + fileName;
        String savePath = uploadEc2 + saveName;
        // ?????? ??????
        Path path = Paths.get(savePath).toAbsolutePath();
        try {
            memberImageFile.transferTo(path);
        } catch (IOException e) {
            throw new BusinessException(ExceptionCode.FILE_NOT_SAVED);
        }

        return saveName;
    }


    // ???????????? ????????? ????????? ?????? ??????
    public String makeFolder(String memberId){

        /* ?????? Data ?????? exam.jpg?????? ????????? ???????????? ??????,
        ???????????? "Data\\"eaxm.jpg", ???????????? "Data/exam.jpg"
        ??????????????? "Data" +File.separator + "exam.jpg" */
        String folderPath = String.valueOf(memberId);

        //make folder, File newFile= new File(dir,"?????????");
        File uploadPathFolder = new File( uploadEc2 + File.separator + uploadPath, folderPath);

        //?????? uploadPathFolder??? ???????????????????????? makeDirectory
        if(uploadPathFolder.exists() == false){

            //mkdir(): ??????????????? ?????? ??????????????? ???????????? ????????????, ????????? ?????????
            //mkdirs(): ??????????????? ?????? ??????????????? ???????????? ?????? ??????, ?????? ?????????????????? ?????? ??????
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

    // ?????? ??????
    public void deleteFile(String fileName){
        String pathName = uploadEc2 + fileName;
        File file = new File(pathName);
        file.delete();
    }

    // ?????? ????????? ?????? ??????
    public boolean checkNickName(String memberNickname){

        return memberRepository.findByMemberNicknameEquals(memberNickname).isEmpty();
    }
}
