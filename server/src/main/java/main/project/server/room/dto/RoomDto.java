package main.project.server.room.dto;


import lombok.*;

public class RoomDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

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

    }
}
