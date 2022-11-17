package main.project.server.room.dto;


import lombok.*;
import main.project.server.room.entity.enums.RoomStatus;

public class RoomDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

//        private long guestHouseId;

        private String roomName;

        private int price;

        private String roomInfo;

        private int roomCapacity;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Put {

        private long roomId;

        private String roomName;

        private int price;

        private String roomInfo;

        private int roomCapacity;
    }

    public static class Response {

        private long roomId;

        private String roomName;

        private int price;

        private String roomImageUrl;

        private String roomInfo;

        private int roomCapacity;

        private RoomStatus roomStatus;
    }
}
