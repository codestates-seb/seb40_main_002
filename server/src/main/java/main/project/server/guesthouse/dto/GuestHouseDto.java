package main.project.server.guesthouse.dto;

import lombok.*;
import main.project.server.annotation.validation.filed.GuestHouseDetails;
import main.project.server.annotation.validation.filed.ValidAddress;
import main.project.server.guesthouse.entity.enums.GuestHouseStatus;
import main.project.server.review.dto.ReviewDto;
import main.project.server.guesthouse.room.dto.RoomDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

public class GuestHouseDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post{ //숙소 등록시 사용하는 Post

        @Pattern(regexp = "^[a-zA-Z가-힣]*$")
        @NotBlank(message = "게스트하우스 이름은 빈값일 수 없습니다")
        private String guestHouseName;

        @Positive
        @NotNull
        private Long cityId;

        @NotBlank
        private String guestHouseLocation;


        private String[] guestHouseAddress;


        @NotBlank
        private String guestHousePhone;


        private Boolean[] guestHouseDetails;

        @NotNull
        private String[] guestHouseTag;

        @NotBlank
        private String guestHouseInfo;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Put{ //숙소 수정시 사용하는 Put

        //게스트 하우스는 패스 파라미터에서 꺼내서 사용
        @Pattern(regexp = "^[a-zA-Z가-힣]*$")
        @NotBlank(message = "게스트하우스 이름은 빈값일 수 없습니다")
        private String guestHouseName;


        @NotNull
        private Long cityId;

        @NotBlank
        private String guestHouseLocation;


        private String[] guestHouseAddress;


        @NotBlank
        private String guestHousePhone;


        private Boolean[] guestHouseDetails;

        @NotNull
        private String[] guestHouseTag;

        @NotBlank
        private String guestHouseInfo;

    }


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class response { //숙소 상세 정보시 사용하는 Response

        private Long guestHouseId;

        private String guestHouseName;

        //멤버 관련
        private String memberId;

        private String memberNickname;

        private String memberPhone;

        private String memberImageUrl;

        //--멤버 관련

        private String guestHouseLocation;

        private String[] guestHouseAddress;

        private String guestHousePhone;

        private GuestHouseStatus guestHouseStatus;

        private Boolean[] guestHouseDetails;


        private Float guestHouseStar;

        private String[] guestHouseTag;

        private List<String> guestHouseImage;

        private String guestHouseInfo;

        private List<RoomDto.Response> rooms;

        private List<ReviewDto.Response> reviews;   // 리뷰

        private Long guestHouseReviewCount;

        private String createdAt;

        private String modifiedAt;
    }

    // 숙소에 대한 간단한 정보(id, 이름, 사진) response
    @Setter
    @Getter
    @NoArgsConstructor
    public static class ResponseSimple {
        private Long guestHouseId;
        private String guestHouseName;
        private String mainImageUrl;
        private String[] guestHouseTag;
        private Integer minRoomPrice;
        private Float guestHouseStar;
    }
}
