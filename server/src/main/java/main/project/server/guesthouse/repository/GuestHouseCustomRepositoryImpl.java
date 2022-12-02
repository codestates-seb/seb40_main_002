package main.project.server.guesthouse.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import main.project.server.guesthouse.entity.GuestHouse;

import main.project.server.guesthouse.entity.QGuestHouse;
import main.project.server.guesthouse.room.entity.QRoom;
import main.project.server.guesthouse.room.entity.enums.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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


//        jpaQueryFactory.select(guestHouse)
//                .from(guestHouse)
//                .join(room).on(guestHouse.guestHouseId.eq(room.guestHouse.guestHouseId))
//                .where(guestHouse.city.cityId.eq(Long.valueOf(cityId)),
//                        guestHouse.guestHouseTag.li,
//                        room.roomStatus.eq(RoomStatus.ROOM_ENABLE),
//                        guestHouse.guestHouseId.no
//                        )

        return null;
    }
}
