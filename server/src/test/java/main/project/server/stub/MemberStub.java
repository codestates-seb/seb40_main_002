package main.project.server.stub;

import main.project.server.member.entity.Member;

public class MemberStub {

    public static Member getAgeMember(String memberId, String memberBirth) {
        return Member.builder().memberId(memberId).memberBirth(memberBirth).build(); // 20대
    }
}
