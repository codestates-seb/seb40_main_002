package main.project.server.chart.repository;

import main.project.server.chart.dto.ChartDto;

import java.util.List;

public interface CustomizedRoomReservationRepository {
    List<ChartDto.MonthlyReservation> findByYearGroupByMonth(Long guestHouseId, int year);
}
