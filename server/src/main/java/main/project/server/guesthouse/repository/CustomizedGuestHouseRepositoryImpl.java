package main.project.server.guesthouse.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.entity.QGuestHouse;
import main.project.server.room.entity.QRoom;
import org.springframework.stereotype.Repository;

import java.util.List;



//
//@Repository
//@RequiredArgsConstructor
//public class CustomizedGuestHouseRepositoryImpl implements CustomizedGuestHouseRepository{
//
////    private final JPAQueryFactory jpaQueryFactory;
////
////    @Override
////    public List<GuestHouse> findAllGuestHouseWhereCityAndId() {
////
////        jpaQueryFactory.selectFrom(QGuestHouse.guestHouse)
////                .join(QRoom.room);
////    }
//}
