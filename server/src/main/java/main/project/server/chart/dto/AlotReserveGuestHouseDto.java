package main.project.server.chart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlotReserveGuestHouseDto {

    Long guestHouseId;
    String guestHouseName;
//    String guestHouseImageUrl;
    Long totalCount;

}
