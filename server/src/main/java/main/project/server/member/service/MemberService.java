package main.project.server.member.service;

import lombok.RequiredArgsConstructor;
import main.project.server.member.entity.Member;
import main.project.server.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findMember(String memberId) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember;
    }
}
