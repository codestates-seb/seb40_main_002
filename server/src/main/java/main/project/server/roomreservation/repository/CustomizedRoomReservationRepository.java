package main.project.server.roomreservation.repository;

import main.project.server.member.entity.Member;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.statistics.condition.SearchCondition;
import main.project.server.statistics.dto.AgeChartDto;
import main.project.server.statistics.dto.MonthlyReservationChartDto;

import java.util.List;

public interface CustomizedRoomReservationRepository {
    List<MonthlyReservationChartDto.MonthlyReservation> findByYearGroupByMonth(Long guestHouseId, int year);

    List<AgeChartDto.AgeGroupReservation> findGroupByAge(Long guestHouseId, SearchCondition condition);

    List<RoomReservation> findMyReservationByGuestHouse(Member member, SearchCondition condition);
}
