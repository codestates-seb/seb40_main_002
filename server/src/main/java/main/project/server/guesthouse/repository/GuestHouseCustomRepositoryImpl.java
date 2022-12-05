package main.project.server.guesthouse.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import main.project.server.guesthouse.entity.GuestHouse;

import main.project.server.guesthouse.entity.QGuestHouse;
import main.project.server.guesthouse.room.entity.QRoom;
import main.project.server.guesthouse.room.entity.enums.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static main.project.server.guesthouse.entity.QGuestHouse.guestHouse;
import static main.project.server.guesthouse.room.entity.QRoom.room;
import static main.project.server.roomreservation.entity.QRoomReservation.roomReservation;

@RequiredArgsConstructor
public class GuestHouseCustomRepositoryImpl implements GuestHouseCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;


    //loe <=
    //goe >=

    @Override
    public Page<GuestHouse> findGuestHouseByFilter(Integer cityId,
                                                   String like,
                                                   String start,
                                                   String end,
                                                   Pageable pageable) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        BooleanBuilder where = new BooleanBuilder();

        if(cityId != null)
            where.and(guestHouse.city.cityId.eq(Long.valueOf(cityId)));

        if(like != null)
            where.and(guestHouse.guestHouseTag.like(like));

        where.and(room.roomStatus.eq(RoomStatus.ROOM_ENABLE));

        BooleanBuilder subWhere1 = new BooleanBuilder();
        subWhere1.and(
                roomReservation.roomReservationStart.loe(startDate))
                .and(roomReservation.roomReservationEnd.goe(startDate))
                .and(roomReservation.roomReservationStart.loe(endDate))
                .and(roomReservation.roomReservationEnd.loe(endDate));


        BooleanBuilder subWhere2 = new BooleanBuilder();
        subWhere2.and(
                roomReservation.roomReservationStart.goe(startDate))
                .and(roomReservation.roomReservationEnd.goe(startDate))
                .and(roomReservation.roomReservationStart.loe(endDate))
                .and(roomReservation.roomReservationEnd.loe(endDate));



        BooleanBuilder subWhere3 = new BooleanBuilder();
        subWhere3.and(
                roomReservation.roomReservationStart.goe(startDate))
                .and(roomReservation.roomReservationEnd.goe(startDate))
                .and(roomReservation.roomReservationStart.loe(endDate))
                .and(roomReservation.roomReservationEnd.goe(endDate));



        BooleanBuilder subWhere4 = new BooleanBuilder();
        subWhere4.and(
                roomReservation.roomReservationStart.loe(startDate))
                .and(roomReservation.roomReservationEnd.goe(startDate))
                .and(roomReservation.roomReservationStart.loe(endDate))
                .and(roomReservation.roomReservationEnd.goe(endDate));


        where.and(
                room.notIn(
                        select(roomReservation.room)
                                .from(roomReservation)
                                .where(subWhere1.or(subWhere2).or(subWhere3).or(subWhere4))));



        List<GuestHouse> guestHouses = jpaQueryFactory.select(guestHouse)
                .from(guestHouse)
                .join(room).on(guestHouse.guestHouseId.eq(room.guestHouse.guestHouseId))
                .where(where)
                .groupBy(guestHouse.guestHouseId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return new PageImpl<>(guestHouses, pageable, guestHouses.size());
    }

    @Override
    public Page<GuestHouse> findAllGuestHouse(String[] tags, Pageable pageable) {

        BooleanBuilder tagBuilder = new BooleanBuilder();

        if(tags != null)
        {
            for (String t : tags) {
                tagBuilder.or(guestHouse.guestHouseTag.like(t));
            }
        }

        List<GuestHouse> guestHouseList = jpaQueryFactory
                .select(guestHouse)
                .from(guestHouse)
                .where(tagBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(guestHouseList, pageable, guestHouseList.size());
    }




}
