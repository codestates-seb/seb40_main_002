package main.project.server.room.dto;


import lombok.*;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.room.entity.enums.RoomStatus;

public class RoomDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

//        private long guestHouseId;

        private String roomName;

        private int roomPrice;

        private String roomInfo;

        private int roomCapacity;
    }
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Put {

        private long roomId;

        private String roomName;

        private int roomPrice;

        private String roomInfo;

        private int roomCapacity;
    }

    @Getter
    @Builder
    public static class Response {

        private long roomId;

        private String roomName;

        private int roomPrice;

        private String roomImageUrl;

        private String roomInfo;

        private int roomCapacity;

        private RoomStatus roomStatus;

    }
}
