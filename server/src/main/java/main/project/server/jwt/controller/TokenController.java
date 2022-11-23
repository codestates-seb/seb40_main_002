package main.project.server.jwt.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.jwt.JwtTokenizer;
import main.project.server.jwt.dto.TokenDto;
import main.project.server.jwt.entity.RefreshToken;
import main.project.server.jwt.mapper.TokenMapper;
import main.project.server.jwt.service.TokenService;
import main.project.server.member.entity.Member;
import main.project.server.oauth.handler.OauthSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    @Getter
    @Value("${jwt.secret-key}")
    private String secretKey;

    private final JwtTokenizer jwtTokenizer;

    private final OauthSuccessHandler oauthSuccessHandler;

    private final TokenService tokenService;

    private final TokenMapper tokenMapper;

    @PostMapping("/api/token")
    public ResponseEntity getAccessToken(HttpServletResponse response, @RequestBody @Valid TokenDto tokenDto) {


        RefreshToken refreshToken = tokenMapper.tokenPostToRefreshToken(tokenDto);

        // refresh 토큰 검사, memberId 추출
        String encodedBase64SecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);

        String memberIdFromToken = jwtTokenizer.getMemberIdFromToken(refreshToken.getRefreshToken(), encodedBase64SecretKey);

        String accessToken = oauthSuccessHandler.delegateAccessToken(Member.Member(memberIdFromToken));
        response.addHeader("Authorization", accessToken);
        System.out.println(accessToken);

        return new ResponseEntity("토큰발급 완료", HttpStatus.OK);
    }

}
