package main.project.server.member.service;

import lombok.RequiredArgsConstructor;
import main.project.server.member.entity.Member;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberStatus;
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
        if(member.getMemberNationality().equals("LOCAL")) member.setMemberNationality(MemberNationality.LOCAL);
        else if(member.getMemberNationality().equals("FOREIGN")) member.setMemberNationality(MemberNationality.FOREIGN);
//        else throw
        member.setMemberStatus(MemberStatus.MEMBER_ENABLE);
        return memberRepository.save(member);
    }


    public Member patchMember(Member member) {
        Member patchMember = memberRepository.findById(member.getMemberId()).get();
        // post 항목 고려 필요(Dto)
        if(member.getMemberNickname() != null) patchMember.setMemberNickname(member.getMemberNickname());
        if(member.getMemberEmail() != null) patchMember.setMemberEmail(member.getMemberEmail());
        if(member.getMemberPhone() != null) patchMember.setMemberPhone(member.getMemberPhone());
//        if(member.getMemberStatus() != null) patchMember.setMemberStatus(member.getMemberStatus());
        if(member.getMemberBirth() != null) patchMember.setMemberBirth(member.getMemberBirth());
        if(member.getMemberNationality() != null) patchMember.setMemberNationality(member.getMemberNationality());
        if(member.getMemberRegisterKind() != null) patchMember.setMemberRegisterKind(member.getMemberRegisterKind());
        if(member.getMemberImageUrl() != null) patchMember.setMemberImageUrl(member.getMemberImageUrl());
        if(member.getMemberTags() != null) patchMember.setMemberTags(member.getMemberTags());

        return patchMember;
    }


    public void deleteMember(Member member) {

    }
}
