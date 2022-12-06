package main.project.server.hidden;


import lombok.RequiredArgsConstructor;
import main.project.server.member.entity.Member;
import main.project.server.member.service.MemberService;
import main.project.server.security.jwt.entity.RefreshToken;
import main.project.server.security.jwt.repository.RefreshTokenRepository;
import main.project.server.security.jwt.service.JwtTokenizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Validated
@RestController
@RequiredArgsConstructor
public class HiddenController {

    private final JwtTokenizer jwtTokenizer;

    private final RefreshTokenRepository refreshTokenRepository;

    private final MemberService memberService;

    @GetMapping("/hidden/token")
    public ResponseEntity getToken(String memberId) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);
        claims.put("memberRoles", List.of("ADMIN"));

        String subject = memberId;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));

        Member member = memberService.findVerifiedMember(memberId);

        RefreshToken token = RefreshToken.builder()
                .refreshToken(refreshToken)
                .member(member)
                .build();

        refreshTokenRepository.save(token);

        return new ResponseEntity(accessToken + "\n" + refreshToken , HttpStatus.OK);
    }
}
