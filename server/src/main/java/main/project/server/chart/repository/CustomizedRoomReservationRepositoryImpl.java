package main.project.server.chart.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import main.project.server.chart.dto.ChartDto;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;

import java.util.List;

import static main.project.server.roomreservation.entity.QRoomReservation.roomReservation;

@RequiredArgsConstructor
public class CustomizedRoomReservationRepositoryImpl implements CustomizedRoomReservationRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ChartDto.MonthlyReservation> findByYearGroupByMonth(Long guestHouseId, int year) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                ChartDto.MonthlyReservation.class,
                                roomReservation.roomReservationStart.month(),
                                roomReservation.count()))
                .from(roomReservation)
                .where(roomReservation.guestHouse.guestHouseId.eq(guestHouseId)
                        .and(roomReservation.roomReservationStatus.eq(RoomReservationStatus.RESERVATION_COMPLETE))
                        .and(roomReservation.roomReservationStart.year().eq(year)))
                .groupBy(roomReservation.roomReservationStart.month())
                .fetch();
    }
}
