package main.project.server.guesthouse.stub;

import main.project.server.member.entity.Member;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberRegisterKind;
import main.project.server.member.entity.enums.MemberStatus;

import java.util.List;

public class MemberStub {

    static List<Member> members;

    static{

        members = List.of(
                Member.builder()
                        .memberId("업주@google")
                        .memberNickname("제뉴어리")
                        .memberEmail("asdf@naver.com")
                        .memberPhone("010-1234-2222")
                        .memberStatus(MemberStatus.MEMBER_ENABLE)
                        .memberBirth("1992-10-12")
                        .memberNationality(MemberNationality.LOCAL)
                        .memberRegisterKind(MemberRegisterKind.GOOGLE)
                        .memberImageUrl(null)
                        .memberTags("|감성||눈|")
                        .memberRoles(List.of("USER", "ADMIN")).build(),
                Member.builder()
                        .memberId("일반회원@naver")
                        .memberNickname("악토버")
                        .memberEmail("asdf@naver.com")
                        .memberPhone("010-1234-2222")
                        .memberStatus(MemberStatus.MEMBER_ENABLE)
                        .memberBirth("1912-10-11")
                        .memberNationality(MemberNationality.LOCAL)
                        .memberRegisterKind(MemberRegisterKind.NAVER)
                        .memberImageUrl(null)
                        .memberTags("|오션뷰||한라봉|")
                        .memberRoles(List.of("USER")).build());

    }

    public static Member getMockMember(int i) {
        return members.get(i);
    }

    public static Member getEmptyMember() {

        return Member.builder()
                .memberNickname("악토버")
                .memberEmail("asdf@naver.com")
                .memberPhone("010-1234-2222")
                .memberStatus(MemberStatus.MEMBER_ENABLE)
                .memberBirth("1912-10-11")
                .memberNationality(MemberNationality.LOCAL)
                .memberRegisterKind(MemberRegisterKind.NAVER)
                .memberImageUrl(null)
                .memberTags("|오션뷰||한라봉|")
                .memberRoles(List.of("USER")).build();
    }

}
