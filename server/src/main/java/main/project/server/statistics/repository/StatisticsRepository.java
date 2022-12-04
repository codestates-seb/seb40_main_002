package main.project.server.statistics.repository;

import main.project.server.statistics.dto.ReserveCountOfGuestHouseDto;

import java.util.List;

public interface StatisticsRepository {

    List<ReserveCountOfGuestHouseDto>  getChartOfGuestHouseForReserveCountInPeriod(Integer[] yearMonthDay, Integer limit);


}
