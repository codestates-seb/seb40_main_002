package main.project.server.hidden;


import lombok.RequiredArgsConstructor;
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

    @GetMapping("/hidden/token")
    public ResponseEntity getToken(String memberId) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);
        claims.put("memberRoles", List.of("ADMIN"));

        String subject = memberId;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));

        return new ResponseEntity(accessToken, HttpStatus.OK);
    }
}
