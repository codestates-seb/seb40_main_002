package main.project.server.security.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.exception.AuthException;
import main.project.server.exception.BusinessException;
import main.project.server.exception.ExceptionCode;
import main.project.server.security.jwt.entity.RefreshToken;
import main.project.server.security.jwt.repository.RefreshTokenRepository;
import main.project.server.member.entity.Member;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void verifyToken(String memberId, String token) {
        RefreshToken refreshToken = findVerifiedToken(memberId);
        if (!refreshToken.getRefreshToken().equals(token)) throw new AuthException(ExceptionCode.INVALID_TOKEN);
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
