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
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import static main.project.server.guesthouse.entity.QGuestHouse.guestHouse;
import static main.project.server.guesthouse.room.entity.QRoom.room;

@RequiredArgsConstructor
public class GuestHouseCustomRepositoryImpl implements GuestHouseCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<GuestHouse> findGuestHouseByFilter(Integer cityId,
                                                   String like,
                                                   String start,
                                                   String end,
                                                   Pageable pageable) {
//        BooleanBuilder where = new BooleanBuilder();
//
//        if(cityId != null)
//            where.and(guestHouse.city.cityId.eq(Long.valueOf(cityId)));
//
//        if(like != null)
//            where.and(guestHouse.guestHouseTag.like(like));
//
//        where.and(room.roomStatus.eq(RoomStatus.ROOM_ENABLE));
//
//        where.and(guestHouse.notIn())
//
//
//
//
//        jpaQueryFactory.select(guestHouse)
//                .from(guestHouse)
//                .join(room).on(guestHouse.guestHouseId.eq(room.guestHouse.guestHouseId))
//                .where(where);
//
//


        return null;
    }

//    Page<GuestHouse> findGuestHouseByFilter(
//            @Param("cityId")Integer cityId,
//            @Param("like")String like,
//            @Param("start")String start,
//            @Param("end")String end,
//            Pageable pageable);

}
