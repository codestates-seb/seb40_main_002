package main.project.server.heart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.project.server.guesthouse.dto.GuestHouseDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class HeartDto {


    @Setter
    @Getter
    @NoArgsConstructor
    public static class ResponseMyPage {

        private Long heartId;

        private GuestHouseDto.ResponseSimple guestHouse;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDateTime createdAt;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        private LocalDateTime modifiedAt;
    }
}
