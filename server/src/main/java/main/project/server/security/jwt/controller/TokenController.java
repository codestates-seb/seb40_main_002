package main.project.server.security.jwt.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.dto.SingleResponseDto;
import main.project.server.security.jwt.service.JwtTokenizer;
import main.project.server.security.jwt.dto.TokenDto;
import main.project.server.security.jwt.entity.RefreshToken;
import main.project.server.security.jwt.mapper.TokenMapper;
import main.project.server.member.entity.Member;
import main.project.server.member.service.MemberService;
import main.project.server.security.jwt.service.TokenService;
import main.project.server.security.oauth.handler.OauthSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class TokenController {

    @Getter
    @Value("${jwt.secret-key}")
    private String secretKey;

    private final JwtTokenizer jwtTokenizer;

    private final OauthSuccessHandler oauthSuccessHandler;

    private final MemberService memberService;

    private final TokenMapper tokenMapper;

    private final TokenService tokenService;


    // refreshToken을 이용해 새로운 accessToken 발급 요청
    @PostMapping("/api/token")
    public ResponseEntity getAccessToken(@RequestBody @Valid TokenDto tokenDto) {


        RefreshToken refreshToken = tokenMapper.tokenPostToRefreshToken(tokenDto);

        String encodedBase64SecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);

        // refresh 토큰 검사, memberId 추출
        String memberIdFromToken = jwtTokenizer.getMemberIdFromToken(refreshToken.getRefreshToken(), encodedBase64SecretKey);
        tokenService.findVerifiedToken(memberIdFromToken);

        Member member = memberService.findVerifiedMember(memberIdFromToken);
        String accessToken = oauthSuccessHandler.delegateAccessToken(member);

        log.info("accessToken = {}", accessToken);

        return new ResponseEntity(accessToken, HttpStatus.OK);
    }
}
