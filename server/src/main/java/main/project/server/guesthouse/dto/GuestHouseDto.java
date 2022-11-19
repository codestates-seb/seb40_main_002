package main.project.server.guesthouse.dto;

import lombok.*;
import main.project.server.guesthouse.entity.enums.GuestHouseStatus;
import main.project.server.guesthousedetails.dto.GuestHouseDetailsDto;
import main.project.server.room.dto.RoomDto;

import java.util.List;

public class GuestHouseDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post{ //숙소 등록시 사용하는 Post

        private String guestHouseName;

        //업주 멤버 아이디는 Principal에서 꺼내서 사용

        private Long cityId;

        private String guestHouseLocation;

        private String guestHouseAddress;

        private String guestHousePhone;

        //상태 받지 않음


        private String[] guestHouseTag;

        private String guestHouseInfo;

    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Put{ //숙소 수정시 사용하는 Put

        //게스트 하우스는 패스 파라미터에서 꺼내서 사용

        private String guestHouseName;

        //업주 멤버 아이디는 Principal에서 꺼내서 사용

        private Long cityId;

        //하위 도시 삭제

        private String guestHouseLocation;

        private String guestHouseAddress;

        private String guestHousePhone;

        //상태 받지 않음

        private String[] guestHouseTag;

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

        private String guestHouseAddress;

        private String guestHousePhone;

        private GuestHouseStatus guestHouseStatus;

        private GuestHouseDetailsDto.Response guestHouseDetails;


        private Float guestHouseStar;

        private String[] guestHouseTag;

        private List<String> guestHouseImage;

        private String guestHouseInfo;

        private List<RoomDto.Response> guestHouseRoom;

        private String createdAt;

        private String modifiedAt;
    }
}
