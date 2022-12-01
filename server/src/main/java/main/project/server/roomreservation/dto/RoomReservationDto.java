package main.project.server.roomreservation.dto;

import lombok.*;
import main.project.server.guesthouse.room.dto.RoomDto;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class RoomReservationDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDate roomReservationStart;
        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDate roomReservationEnd;
    }


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long guestHouseId;

        private Long roomReservationId;

        private String guestHouseName;

        private String roomName;

        private String roomImageUrl;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDate roomReservationStart;
        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDate roomReservationEnd;

        private RoomReservationStatus roomReservationStatus;

    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseSimple {
        private RoomDto.Response room;
        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDate roomReservationStart;
        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDate roomReservationEnd;
    }
}
