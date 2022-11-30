package main.project.server.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.exception.BusinessException;
import main.project.server.exception.ExceptionCode;
import main.project.server.jwt.JwtTokenizer;
import main.project.server.jwt.entity.RefreshToken;
import main.project.server.jwt.repository.RefreshTokenRepository;
import main.project.server.member.entity.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenizer jwtTokenizer;

    public void reSetAuthToContext(String accessToken) {
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); // JWT 서명(Signature)을 검증하기 위한 Secret Key
        Map<String, Object> claims = jwtTokenizer.getClaims(accessToken, base64EncodedSecretKey).getBody(); // 파싱에 성공하면 서명 검증에 성공한 것

        String id = (String) claims.get("memberId");
        List<String> roles = (List<String>) claims.get("memberRoles");
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(id, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public RefreshToken findVerifiedToken(String memberId) {
        Optional<RefreshToken> optional =
                refreshTokenRepository.findByMember(Member.Member(memberId));
        RefreshToken findRefreshToken = optional.orElseThrow(() -> new BusinessException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));
        return findRefreshToken;
    }

    public void deleteToken(String memberId) {
        RefreshToken findRefreshToken = findVerifiedToken(memberId);
        refreshTokenRepository.delete(findRefreshToken);
    }
}
