package main.project.server.member.service;

import lombok.RequiredArgsConstructor;
import main.project.server.member.entity.Member;
import main.project.server.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findMember(String memberId) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember;
    }


    public Member createMember(Member member) {

        return memberRepository.save(member);
    }


    public Member putMember(Member member) {
        Member putMember = memberRepository.findById(member.getMemberId()).get();

        return putMember;
    }


    public void deleteMember(Member member) {

        memberRepository.delete(member);
    }
}
