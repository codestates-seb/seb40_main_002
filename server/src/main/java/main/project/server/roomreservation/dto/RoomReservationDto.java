package main.project.server.roomreservation.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class RoomReservationDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

//        private Long roomId;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime roomReservationStart;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime roomReservationEnd;
    }
}
