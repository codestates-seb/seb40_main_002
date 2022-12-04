package main.project.server.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.project.server.member.dto.MemberDto;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ReviewCommentDto {

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Post {

        @NotBlank
        private String reviewComment;
    }


    @Setter
    @Getter
    @NoArgsConstructor
    public static class Put {

        @NotBlank
        private String reviewComment;
    }


    @Setter
    @Getter
    @NoArgsConstructor
    public static class Response {

        private Long reviewCommentId;

        private String reviewComment;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDateTime createdAt;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDateTime modifiedAt;

        private MemberDto.Response user;
    }
}
