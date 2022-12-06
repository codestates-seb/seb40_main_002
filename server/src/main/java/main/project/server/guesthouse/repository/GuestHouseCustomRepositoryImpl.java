package main.project.server.guesthouse.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.room.entity.enums.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import java.time.LocalDate;
import java.util.List;
import static com.querydsl.jpa.JPAExpressions.select;
import static main.project.server.guesthouse.entity.QGuestHouse.guestHouse;
import static main.project.server.guesthouse.room.entity.QRoom.room;
import static main.project.server.roomreservation.entity.QRoomReservation.roomReservation;

@RequiredArgsConstructor
public class GuestHouseCustomRepositoryImpl implements GuestHouseCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    //-- loe 의미 : <=
    //-- goe 의미 : >=
    @Override
    public Page<GuestHouse> findGuestHouseByFilter(
            Integer cityId,
            String[] tags,
            String start,
            String end,
            Pageable pageable,
            String sort)
    {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        // 지역 세팅
        BooleanBuilder where = new BooleanBuilder();
        
        if(cityId != null)
        {
            if(cityId >= 1 && cityId <= 13) //DB에 저장되어 있는 코드 범위가 아니면 검색 조건 자체에 넣지 않음
                where.and(guestHouse.city.cityId.eq(Long.valueOf(cityId)));
            
        }

        // 태그 세팅
        BooleanBuilder tagBuilder = new BooleanBuilder();
        if (tags != null && tags.length != 0) {

            for (String t : tags) {

                //태그가 여러개일 경우, 필터링시 모든 태그가 부합되야만 하는것이 아닌 부분적으로 일치만 하여도 되도록 or 사용
                tagBuilder.or(guestHouse.guestHouseTag.like(t));
            }
            where.and(tagBuilder);
        }

        // 소팅 세팅
        OrderSpecifier orderSpecifier = getOrderSpecifier(sort);

        // 룸 상태가 ROOM_ENABLE 인 룸들만 조회
        where.and(room.roomStatus.eq(RoomStatus.ROOM_ENABLE));

        // 첫번째 예약 기간 조건
        BooleanBuilder subWhere1 = new BooleanBuilder();
        subWhere1.and(
                        roomReservation.roomReservationStart.loe(startDate))
                .and(roomReservation.roomReservationEnd.goe(startDate))
                .and(roomReservation.roomReservationStart.loe(endDate))
                .and(roomReservation.roomReservationEnd.loe(endDate));

        // 두번째 예약 기간 조건
        BooleanBuilder subWhere2 = new BooleanBuilder();
        subWhere2.and(
                        roomReservation.roomReservationStart.goe(startDate))
                .and(roomReservation.roomReservationEnd.goe(startDate))
                .and(roomReservation.roomReservationStart.loe(endDate))
                .and(roomReservation.roomReservationEnd.loe(endDate));

        // 세번째 예약 기간 조건
        BooleanBuilder subWhere3 = new BooleanBuilder();
        subWhere3.and(
                        roomReservation.roomReservationStart.goe(startDate))
                .and(roomReservation.roomReservationEnd.goe(startDate))
                .and(roomReservation.roomReservationStart.loe(endDate))
                .and(roomReservation.roomReservationEnd.goe(endDate));

        // 네번째 예약 기간 조건
        BooleanBuilder subWhere4 = new BooleanBuilder();
        subWhere4.and(
                        roomReservation.roomReservationStart.loe(startDate))
                .and(roomReservation.roomReservationEnd.goe(startDate))
                .and(roomReservation.roomReservationStart.loe(endDate))
                .and(roomReservation.roomReservationEnd.goe(endDate));

        // 지금까지 만들어진 여러 where 절들을 모아서 하나의 where 절로 만듦.
        where.and(
                room.notIn(
                        select(roomReservation.room)
                                .from(roomReservation)

                                // 4가지의 예약기간 케이스에 하나라도 걸린 룸은 예약이 불가한 룸이므로 or 조건절 사용
                                .where(subWhere1.or(subWhere2).or(subWhere3).or(subWhere4))));

        // 하나로 합쳐진 where 절을 포함하여 전체 쿼리 체인을 만듦
        List<GuestHouse> guestHouseList = jpaQueryFactory.select(guestHouse)
                .from(guestHouse)
                .join(room).on(guestHouse.guestHouseId.eq(room.guestHouse.guestHouseId))
                .where(where)
                .groupBy(guestHouse.guestHouseId)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 현재 조건으로 검색되는 게스트하우스의 총 갯수를 알기 위한 count 쿼리
        // group by 부분에서 한번 쿼리를 끊어줘야함. 중복된 데이터들의 갯수를 표현해야 해서 다중 로우가 된것을
        // part.fetchCount()로 중복 제거된 총 로우의 갯수를 가져오면 count를 알 수 있음
        JPQLQuery<GuestHouse> part = jpaQueryFactory.select(guestHouse)
                .from(guestHouse)
                .join(room).on(guestHouse.guestHouseId.eq(room.guestHouse.guestHouseId))
                .where(where)
                .groupBy(guestHouse.guestHouseId);

        // 페이지 객체 생성을 위해 PageableExecutionUtils 사용
        return PageableExecutionUtils.getPage(guestHouseList, pageable, ()-> part.fetchCount()); //part.fetchCount() : (페이지네이션으로 인해 출력될 데이터의 총 갯수)
    }

    @Override
    public Page<GuestHouse> findAllGuestHouse(String[] tags, Pageable pageable, String sort) {

        // 소팅 세팅
        OrderSpecifier orderSpecifier = getOrderSpecifier(sort);

        // 태그 세팅
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
