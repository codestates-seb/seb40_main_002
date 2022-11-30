package main.project.server.guesthouse.repository;

import main.project.server.chart.dto.AlotReserveGuestHouseDto;
import main.project.server.guesthouse.entity.GuestHouse;

import java.util.List;

public interface CustomizedGuestHouseRepository {

    List<AlotReserveGuestHouseDto>  getChartOfGuestHouseForReserveCountInPeriod(Short[] yearMonthDay);
}
