package main.project.server.reservation;


import main.project.server.city.entity.City;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.repository.GuestHouseRepository;
import main.project.server.guesthouse.room.entity.Room;
import main.project.server.guesthouse.room.entity.enums.RoomStatus;
import main.project.server.member.entity.Member;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import main.project.server.roomreservation.repository.RoomReservationRepository;
import main.project.server.roomreservation.service.RoomReservationService;
import main.project.server.stub.ReservationStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ActiveProfiles("dev")
@Transactional
@SpringBootTest
public class RoomReservationTests {

    @Autowired
    private RoomReservationRepository roomReservationRepository;
    @Autowired
    GuestHouseRepository guestHouseRepository;


    @Autowired
    RoomReservationService roomReservationService;


    @DisplayName("예약 검증 - 1번 케이스 예약 상태에서 예약 가능 여부 확인")
    @Test
    void verifyReservationTest1() {

        //given
        Member member = Member.builder().memberId("2526022361@kakao").build();
        City city = City.City(1L);
        String start = "2023-02-10";
        String end = "2023-02-13";
        PageRequest pageRequest = PageRequest.of(0, 10);

        GuestHouse guestHouse = GuestHouse.builder().guestHouseId(null).guestHouseName("1번 케이스").member(member).city(city).guestHouseTag("|감성|").build();
        Room room = Room.builder().roomId(null).roomName("1-1룸").guestHouse(guestHouse).roomStatus(RoomStatus.ROOM_ENABLE).build();
        guestHouse.setRooms(List.of(room));
        guestHouseRepository.save(guestHouse);


        //1번 케이스 예약
        RoomReservation existsRoomReservation = RoomReservation.builder()
                .room(room)
                .member(member)
                .roomReservationStart(LocalDate.parse(start))
                .roomReservationEnd(LocalDate.parse(end))
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE).build();


        roomReservationRepository.save(existsRoomReservation);


        //when
        RoomReservation roomReservation = RoomReservation.builder()
                .room(room)
                .member(member)
                .roomReservationStart(LocalDate.parse("2023-02-12"))
                .roomReservationEnd(LocalDate.parse("2023-02-19"))
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE).build();
//
//        roomReservationRepository.




        //then



    }

}
