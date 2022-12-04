package main.project.server.security.jwt.repository;

import main.project.server.security.jwt.entity.RefreshToken;
import main.project.server.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}
