//package main.project.server.guesthouse.repository;
//
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import main.project.server.city.entity.City;
//import main.project.server.guesthouse.entity.GuestHouse;
//import main.project.server.guesthouse.entity.QGuestHouse;
//import main.project.server.room.entity.QRoom;
//import main.project.server.roomreservation.entity.QRoomReservation;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.IntStream;
//
//import static main.project.server.guesthouse.entity.QGuestHouse.*;
//import static main.project.server.room.entity.QRoom.room;
//import static main.project.server.roomreservation.entity.QRoomReservation.*;
//
//
//@Repository
//@RequiredArgsConstructor
//public class CustomizedGuestHouseRepositoryImpl implements CustomizedGuestHouseRepository{
//    private final JPAQueryFactory jpaQueryFactory;
//
//
//    @Override
//    public List<GuestHouse> getChartOfGuestHouseForReserveCountInPeriod(Short[] yearMonthDay) {
//
////        List<GuestHouse> fetch = jpaQueryFactory.selectFrom(guestHouse)
////                .where(guestHouse.city.eq(City.City(1L)), guestHouse.guestHouseId.in(1,2,10,100))
////                .orderBy(guestHouse.guestHouseStar.desc()).fetch();
//
//
////        List<GuestHouse> fetch = jpaQueryFactory.select(guestHouse).from(guestHouse)
////                .where(guestHouse.city.eq(City.City(1L)), guestHouse.guestHouseId.in(1,2,10,100))
////                .orderBy(guestHouse.guestHouseStar.desc()).fetch();
////
////        QGuestHouse g = guestHouse;
//
//
////        IntStream.range(0, ye)
//
//        jpaQueryFactory.select(guestHouse)
//                .join(guestHouse.roomReservations, roomReservation) //join 함 (연관관계 엔티티, alias)
//                .where(guestHouse.guestHouseId.eq(roomReservation.guestHouse.guestHouseId),
//                        roomReservation.createdAt.between(LocalDateTime.now(), LocalDateTime.now().plusDays(day)) //join 조건 on
//
//    }
//
//
//    private LocalDate getPeriod(Short[] yearMonthDay) { // 년, 월 일
//
//        if (yearMonthDay == null) { //잘못된 상황이지만 그냥 현재 날짜로 반환
//            return LocalDate.now();
//        }
//
//        IntStream.range(0, yearMonthDay.length).forEach(index->{
//
//            if (yearMonthDay[index] != 0) {
//
//                if (index == 0) {
//                    return LocalDate.now().plusYears(yearMonthDay[0])
//                } else if (index == 1) {
//
//                }else {
//
//                }
//            }
//
//        });
//
//
//    }
//}
