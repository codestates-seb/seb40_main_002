package main.project.server.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReserveCountOfGuestHouseDto {

    Long guestHouseId;
    String guestHouseName;
    Long totalCount;

}
