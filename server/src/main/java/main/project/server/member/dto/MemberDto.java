package main.project.server.member.dto;

import lombok.*;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberRegisterKind;
import main.project.server.validation.ValidRole;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class MemberDto {
    // Dto 항목 고려 필요
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post{

        @Id
        private String memberId;
        @NotBlank
        private String memberNickname;
        @Email
        @NotBlank
        private String memberEmail;
        @NotBlank
        private String memberPhone;

//        private MemberStatus memberStatus;

        private String memberBirth;

        private MemberNationality memberNationality;

        private MemberRegisterKind memberRegisterKind; //가입 경로, ex) GOOGLE, NAVER, KAKAO....
        @ValidRole
        private List<String> memberRole; //멤버 역할, ex) USER, ADMIN ...

        private String[] memberTag;

    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Patch{

        private String memberId;

        private String memberNickname;

        private String[] memberTag;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{

        private String memberId;

        private String memberNickname;

        private String memberEmail;

        private String memberPhone;

//        private MemberStatus memberStatus;

        private String memberBirth;

        private MemberNationality memberNationality;

        private MemberRegisterKind memberRegisterKind;

        private String memberImageUrl;

        private List<String> memberRoles;

        private String[] memberTag;
    }
}
