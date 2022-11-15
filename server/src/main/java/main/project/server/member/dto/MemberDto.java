package main.project.server.member.dto;

import lombok.*;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberRegisterKind;
import main.project.server.member.entity.enums.MemberStatus;

import java.time.LocalDateTime;

public class MemberDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post{

        private String memberId;

        private String memberNickname;

        private String memberEmail;

        private String memberPhone;

        private String memberBirth;

        private String memberNationality;

        private String memberRegisterKind;

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

        private MemberStatus memberStatus;

        private LocalDateTime memberBirth;

        private MemberNationality memberNationality;

        private MemberRegisterKind memberRegisterKind;

        private String memberImageUrl;

        private String memberTags;
    }
}
