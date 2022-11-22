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
    }
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Put {

        private Long roomId;

        private String roomName;

        private int roomPrice;

        private String roomInfo;
    }

    @Setter
    @Getter
    @Builder
    public static class Response {

        private long roomId;

        private String roomName;

        private int roomPrice;

        private String roomImageUrl;

        private String roomInfo;

        private Boolean reservePossible; //필드 하나만 추가하면 되기때문에 기존 DTO에 추가, 예약 가능 여부를 보내주기 위해 추가
    }
}
