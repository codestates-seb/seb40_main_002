package main.project.server.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.project.server.member.dto.MemberDto;

import java.time.LocalDateTime;

public class ReviewCommentDto {

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Post {
        private String reviewComment;
    }


    @Setter
    @Getter
    @NoArgsConstructor
    public static class Put {
        private String reviewComment;
    }


    @Setter
    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long reviewCommentId;
        private String reviewComment;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private MemberDto.Response user;
    }
}
