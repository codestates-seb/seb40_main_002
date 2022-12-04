package main.project.server.statistics.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ReserveCountChartDto {

    private int totalCount;
    private List<ReserveCountOfGuestHouseDto> reserveCountOfGuestHouseList;

    private ReserveCountChartDto(List<ReserveCountOfGuestHouseDto> reserveCountOfGuestHouseList) {

        this.totalCount = reserveCountOfGuestHouseList.size();
        this.reserveCountOfGuestHouseList = reserveCountOfGuestHouseList;
    }


    public static ReserveCountChartDto of(List<ReserveCountOfGuestHouseDto> reserveCountOfGuestHouseList) {

        return new ReserveCountChartDto(reserveCountOfGuestHouseList);
    }
}
