package main.project.server.member.dto;

import lombok.*;
import main.project.server.annotation.validation.enumvalid.ValidEnum;
import main.project.server.annotation.validation.filed.ValidTag;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberRegisterKind;
import main.project.server.validation.role.ValidRole;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "010으로 시작하고 숫자와 '-'로 구성된 휴대폰 번호 형식이어야 합니다")
        private String memberPhone;

        @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
                message = "생년월일은 유효한 'yyyy-mm-dd' 형식이어야 합니다.")
        private String memberBirth;

        @ValidEnum(enumClass = MemberNationality.class)
        private MemberNationality memberNationality;

        @ValidEnum(enumClass = MemberRegisterKind.class)
        private MemberRegisterKind memberRegisterKind; //가입 경로, ex) GOOGLE, NAVER, KAKAO....

        @ValidRole
        private List<String> memberRole; //멤버 역할, ex) USER, ADMIN ...
        @ValidTag
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
