package main.project.server.roomreservation.dto;

import lombok.*;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

        private String guestHouseName;

        private String roomName;

        private String roomImageUrl;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDate roomReservationStart;
        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDate roomReservationEnd;

        private RoomReservationStatus roomReservationStatus;

    }

}
