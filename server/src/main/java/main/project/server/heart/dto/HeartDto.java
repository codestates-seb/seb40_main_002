package main.project.server.heart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.project.server.guesthouse.dto.GuestHouseDto;

import java.time.LocalDateTime;

public class HeartDto {


    @Setter
    @Getter
    @NoArgsConstructor
    public static class ResponseMyPage {

        private Long heartId;

        private GuestHouseDto.ResponseSimple guestHouse;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;
    }
}
