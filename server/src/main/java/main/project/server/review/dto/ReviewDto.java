package main.project.server.review.dto;

import lombok.*;
import main.project.server.guesthouse.dto.GuestHouseDto;
import main.project.server.member.dto.MemberDto;
import main.project.server.roomreservation.dto.RoomReservationDto;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReviewDto {

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Post {

        @NotBlank
        private String comment;

        @NotNull
        private float star;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Put {

        @NotBlank
        private String comment;

        @NotNull
        private float star;
    }


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long reviewId;

        private String comment;

        private float star;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDateTime createdAt;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDateTime modifiedAt;

        private MemberDto.Response member;

        private String guestHouseName;

        private String guestHouseMemberId;

        private ReviewCommentDto.Response reviewComment;
    }


    // 마이페이지 리뷰 조회시 responseDto
    @Setter
    @Getter
    @NoArgsConstructor
    public static class ResponseMyPage {

        private Long reviewId;

        private GuestHouseDto.ResponseSimple guestHouse;

        private RoomReservationDto.ResponseSimple roomReservation;

        private String comment;

        private float star;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDateTime createdAt;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDateTime modifiedAt;
    }
}
