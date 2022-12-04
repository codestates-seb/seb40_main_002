package main.project.server.member.service;

import main.project.server.security.jwt.service.JwtTokenizer;
import main.project.server.member.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ActiveProfiles("dev")
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    MemberService memberService;
    @Autowired
    JwtTokenizer jwtTokenizer;
    @Autowired
    RedisTemplate redisTemplate;


    @DisplayName("멤버 서비스 - 멤버 로그아웃 토큰 레디스에 저장")
    @Test
    void registerLogoutTokenTest() {


        //givne
        Member member = Member.builder().memberId("12341234@kakao").memberRoles(List.of("USER","ADMIN")).build();
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("memberRoles", member.getMemberRoles());

        String subject = member.getMemberId();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        System.out.println("memberId = " + member.getMemberId());
        //when
        memberService.registerLogoutToken(accessToken);

        //then
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String value = (String)valueOperations.get("logout@" + accessToken);

        System.out.println("value = " + value);
        Assertions.assertEquals(member.getMemberId(), value);

    }
}
