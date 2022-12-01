package main.project.server.statistics.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import main.project.server.statistics.dto.ReserveCountOfGuestHouseDto;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static main.project.server.guesthouse.entity.QGuestHouse.*;
import static main.project.server.roomreservation.entity.QRoomReservation.*;


@Repository
@RequiredArgsConstructor
public class StatisticsRepositoryImpl implements StatisticsRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReserveCountOfGuestHouseDto> getChartOfGuestHouseForReserveCountInPeriod(Integer[] yearMonthDay, Integer limit) {

        LocalDateTime toLocalDateTime = LocalDateTime.now();
        LocalDateTime fromLocalDateTime = getPeriod(yearMonthDay);



        List<ReserveCountOfGuestHouseDto> reserveCountOfGuestHouseDtoList = jpaQueryFactory
                .select(
                        Projections.constructor
                                (
                                        ReserveCountOfGuestHouseDto.class,
                                        guestHouse.guestHouseId,
                                        guestHouse.guestHouseName,
                                        guestHouse.guestHouseId.count()
                                ))
                .from(guestHouse)
                .leftJoin(guestHouse.roomReservations, roomReservation)
//                .where(
//                        roomReservation.createdAt.between(fromLocalDateTime, toLocalDateTime),
//                        roomReservation.roomReservationStatus.eq(RoomReservationStatus.RESERVATION_COMPLETE)
//                )
                .groupBy(guestHouse.guestHouseId)
                .orderBy(guestHouse.guestHouseId.count().desc())
                .offset(0)
                .limit(limit)
                .fetch();

        return reserveCountOfGuestHouseDtoList;
    }


    private LocalDateTime getPeriod(Integer[] yearMonthDay) { // 년, 월 일

        LocalDateTime result = LocalDate.now().atStartOfDay(); // 년,월,일 - 00.0000 으로 시작하기 위해
        if (yearMonthDay == null) { //잘못된 상황이지만 그냥 현재 날짜로 반환
            return result;
        }

        for (int i = 0; i < yearMonthDay.length; i++) {

            if(i == 0 && yearMonthDay[i] != 0)
                return LocalDate.now().minusYears(yearMonthDay[0]).atStartOfDay();
            else if(i == 1 && yearMonthDay[i] != 0)
                return LocalDate.now().minusMonths(yearMonthDay[1]).atStartOfDay();
            else if(i == 2 && yearMonthDay[i] != 0)
                return LocalDate.now().minusDays(yearMonthDay[2]).atStartOfDay();
        }

        return result;
    }

}
