package main.project.server.review.dto;

import lombok.*;
import main.project.server.guesthouse.dto.GuestHouseDto;
import main.project.server.member.dto.MemberDto;

import java.time.LocalDateTime;

public class ReviewDto {

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Post {
        private String comment;
        private float star;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Put {
        private String comment;
        private float star;
    }


    @Setter
    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long reviewId;
        private String comment;
        private float star;
        private ReviewCommentDto.Response reviewComment;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private MemberDto.Response member;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class ResponseMyPage {
        private Long reviewId;
        private GuestHouseDto.responseSimple guestHouse;
        private String comment;
        private float star;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
