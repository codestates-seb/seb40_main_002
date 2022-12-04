package main.project.server.guesthouse.room.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class RoomDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {
        @NotBlank
        private String roomName;
        @Positive
        private Integer roomPrice;
        @NotBlank
        private String roomInfo;
    }
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Put {
        @Positive
        private Long roomId;
        @NotBlank
        private String roomName;
        @Positive
        private Integer roomPrice;
        @NotBlank
        private String roomInfo;
    }

    @Setter
    @Getter
    @Builder
    public static class Response {

        private Long roomId;

        private String roomName;

        private Integer roomPrice;

        private String roomImageUrl;

        private String roomInfo;

        private boolean reservePossible = false; //필드 하나만 추가하면 되기때문에 기존 DTO에 추가, 예약 가능 여부를 보내주기 위해 추가
    }
}
