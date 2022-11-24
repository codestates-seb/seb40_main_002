package main.project.server.member.repository;

import main.project.server.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByMemberNicknameEquals(String memberNickname);
}
