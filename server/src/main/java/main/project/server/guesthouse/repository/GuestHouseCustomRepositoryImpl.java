package main.project.server.guesthouse.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import main.project.server.guesthouse.entity.GuestHouse;

import main.project.server.guesthouse.entity.QGuestHouse;
import main.project.server.guesthouse.room.entity.QRoom;
import main.project.server.guesthouse.room.entity.enums.RoomStatus;
import org.apache.catalina.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.query.Param;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

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
                                                   String[] tags,
                                                   String start,
                                                   String end,
                                                   Pageable pageable,
                                                   String sort) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);



        //지역 세팅
        BooleanBuilder where = new BooleanBuilder();
        if(cityId != null)
            where.and(guestHouse.city.cityId.eq(Long.valueOf(cityId)));


        //태그 세팅
        BooleanBuilder tagBuilder = new BooleanBuilder();
        if (tags != null && tags.length != 0) {

            for (String t : tags) {
                tagBuilder.or(guestHouse.guestHouseTag.like(t));
            }
            where.and(tagBuilder);
        }

        //소팅 세팅
        OrderSpecifier orderSpecifier = getOrderSpecifier(sort);


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





        List<GuestHouse> guestHouseList = jpaQueryFactory.select(guestHouse)
                .from(guestHouse)
                .join(room).on(guestHouse.guestHouseId.eq(room.guestHouse.guestHouseId))
                .where(where)
                .groupBy(guestHouse.guestHouseId)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        //카운트 쿼리를 직접 해줘야함.
        JPAQuery<Long> countQuery = jpaQueryFactory.select(guestHouse.count())
                .from(guestHouse)
                .join(room).on(guestHouse.guestHouseId.eq(room.guestHouse.guestHouseId))
                .where(where)
                .groupBy(guestHouse.guestHouseId);


        return PageableExecutionUtils.getPage(guestHouseList, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<GuestHouse> findAllGuestHouse(String[] tags, Pageable pageable, String sort) {

        OrderSpecifier orderSpecifier = getOrderSpecifier(sort);

        BooleanBuilder tagBuilder = new BooleanBuilder();

        if (tags != null && tags.length != 0) {

            for (String t : tags) {
                tagBuilder.or(guestHouse.guestHouseTag.like(t));
            }
        }

        List<GuestHouse> guestHouseList = jpaQueryFactory
                .select(guestHouse)
                .from(guestHouse)
                .where(tagBuilder)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        //카운트 쿼리를 직접 해줘야함.
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(guestHouse.count())
                .from(guestHouse)
                .where(tagBuilder);



        return PageableExecutionUtils.getPage(guestHouseList, pageable, countQuery::fetchOne);

    }

    private OrderSpecifier getOrderSpecifier(String sort) {
        OrderSpecifier orderSpecifier;

        if(sort == null || sort.equals(""))
            orderSpecifier = guestHouse.guestHouseId.desc();
        else if (sort.equals("star"))
            orderSpecifier = guestHouse.guestHouseStar.desc();
        else if(sort.equals("review"))
            orderSpecifier = guestHouse.guestHouseReviewCount.desc();
        else
            orderSpecifier = guestHouse.guestHouseId.desc();

        return orderSpecifier;
    }


}
