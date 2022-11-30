package main.project.server.guesthouse.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import main.project.server.chart.dto.AlotReserveGuestHouseDto;
import main.project.server.city.entity.City;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.entity.QGuestHouse;
import main.project.server.room.entity.QRoom;
import main.project.server.roomreservation.entity.QRoomReservation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.stream.IntStream;

import static main.project.server.guesthouse.entity.QGuestHouse.*;
import static main.project.server.guesthouseimage.entity.QGuestHouseImage.guestHouseImage;
import static main.project.server.room.entity.QRoom.room;
import static main.project.server.roomreservation.entity.QRoomReservation.*;


@Repository
@RequiredArgsConstructor
public class CustomizedGuestHouseRepositoryImpl implements CustomizedGuestHouseRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AlotReserveGuestHouseDto> getChartOfGuestHouseForReserveCountInPeriod(Short[] yearMonthDay) {

//        연습
//        List<GuestHouse> fetch = jpaQueryFactory.selectFrom(guestHouse)
//                .where(guestHouse.city.eq(City.City(1L)), guestHouse.guestHouseId.in(1,2,10,100))
//                .orderBy(guestHouse.guestHouseStar.desc()).fetch();
//
//
//        List<GuestHouse> fetch = jpaQueryFactory.select(guestHouse).from(guestHouse)
//                .where(guestHouse.city.eq(City.City(1L)), guestHouse.guestHouseId.in(1,2,10,100))
//                .orderBy(guestHouse.guestHouseStar.desc()).fetch();



        LocalDateTime toLocalDateTime = LocalDateTime.now();
        LocalDateTime fromLocalDateTime = getPeriod(yearMonthDay);

        System.out.println(fromLocalDateTime.toString());
        System.out.println(toLocalDateTime.toString());


//        List<GuestHouse> fetch = jpaQueryFactory
//                .select(
//                        Pr
//                )
//                .from(guestHouse)
//                .fetch();



        List<AlotReserveGuestHouseDto> fetch = jpaQueryFactory
                .select(
                        Projections.constructor
                                (
                                        AlotReserveGuestHouseDto.class,
                                        guestHouse.guestHouseId,
                                        guestHouse.guestHouseName,
//                                        guestHouseImage.guestHouseImageUrl,
                                        guestHouse.guestHouseId.count()
                                ))
                .from(guestHouse)
//                .leftJoin(guestHouse.roomReservations, roomReservation)
//                .leftJoin(guestHouse.guestHouseImage, guestHouseImage) //게스트하우스에 등록된 이미지가 없다는 예외사항을 고려하여 left join
//                .where(roomReservation.createdAt.between(fromLocalDateTime, toLocalDateTime)).groupBy(guestHouse.guestHouseId) //between의 인자는 작은것에서 큰것으로 해야함.
//                .orderBy(guestHouse.guestHouseId.count().desc())
                .groupBy(guestHouse.guestHouseId.count())
                .fetch();








        return null;
    }


    private LocalDateTime getPeriod(Short[] yearMonthDay) { // 년, 월 일

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
            else
                return result;
        }

        return result;
    }

}
