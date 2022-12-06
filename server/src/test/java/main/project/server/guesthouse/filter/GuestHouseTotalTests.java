package main.project.server.guesthouse.filter;


import main.project.server.city.entity.City;
import main.project.server.city.repository.CityRepository;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.repository.GuestHouseRepository;
import main.project.server.guesthouse.room.entity.enums.RoomStatus;
import main.project.server.member.entity.Member;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberRegisterKind;
import main.project.server.member.entity.enums.MemberStatus;
import main.project.server.member.repository.MemberRepository;
import main.project.server.guesthouse.room.entity.Room;
import main.project.server.guesthouse.room.repository.RoomRepository;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import main.project.server.roomreservation.repository.RoomReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ActiveProfiles("dev")
@Transactional
@SpringBootTest
public class GuestHouseTotalTests {

    City[] cities = new City[]{
            new City(1L, "제주시"),
            new City(2L, "조천"),
            new City(3L, "구좌"),
            new City(4L, "우도"),
            new City(5L, "성산"),
            new City(6L, "표선"),
            new City(7L, "남원")};

    Member[] members = new Member[]{
            Member.builder()
                    .memberId("업주@google")
                    .memberNickname("제뉴어리")
                    .memberEmail("asdf@naver.com")
                    .memberPhone("010-1234-2222")
                    .memberStatus(MemberStatus.MEMBER_ENABLE)
                    .memberBirth("1992-10-12")
                    .memberNationality(MemberNationality.LOCAL)
                    .memberRegisterKind(MemberRegisterKind.GOOGLE)
                    .memberImageUrl(null)
                    .memberTags("|감성||눈|")
                    .memberRoles(List.of("USER", "ADMIN")).build(),

            Member.builder()
                    .memberId("일반회원@naver")
                    .memberNickname("악토버")
                    .memberEmail("asdf@naver.com")
                    .memberPhone("010-1234-2222")
                    .memberStatus(MemberStatus.MEMBER_ENABLE)
                    .memberBirth("1912-10-11")
                    .memberNationality(MemberNationality.LOCAL)
                    .memberRegisterKind(MemberRegisterKind.NAVER)
                    .memberImageUrl(null)
                    .memberTags("|오션뷰||한라봉|")
                    .memberRoles(List.of("USER")).build(),
    };

    @Autowired
    CityRepository cityRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GuestHouseRepository guestHouseRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomReservationRepository roomReservationRepository;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    @DisplayName("메인필터 - 예약 가능 숙소 찾기 - 예약이 없는 룸")
    void getGuestHouseMainFilterTest1() {

        //given
        Member member = Member.builder().memberId("2526022361@kakao").build();
        City city = City.City(1L);
        String start = "2023-02-12";
        String end = "2023-02-19";
        PageRequest pageRequest = PageRequest.of(0, 10);

        GuestHouse guestHouse = GuestHouse.builder().guestHouseId(null).guestHouseName("1번 케이스").member(member).city(city).guestHouseTag("|감성|").build();
        Room room = Room.builder().roomId(null).roomName("1-1룸").guestHouse(guestHouse).roomStatus(RoomStatus.ROOM_ENABLE).build();
        guestHouse.setRooms(List.of(room));
        guestHouseRepository.save(guestHouse);



        //where
//        Page<GuestHouse> all = guestHouseRepository.findAll(PageRequest.of(1, 10));

        String[] tags = null;
        String sort = "default";

        Page<GuestHouse> guestHouseByFilter = guestHouseRepository.findGuestHouseByFilter(city.getCityId().intValue(), tags, start, end, pageRequest, sort);


        //then
        //룸만 등록했으므로 존재해야함
        Assertions.assertEquals(true, guestHouseByFilter.getContent().stream().anyMatch(g -> {
            if (g.getGuestHouseId().equals(guestHouse.getGuestHouseId()))
                return true;
            return false;
        }));


    }


    @Test
    @DisplayName("메인필터 - 예약 가능 숙소 찾기 - 1번 케이스, 이미 예약 있는 룸")
    void getGuestHouseMainFilterTest2() {

        //given
        Member member = Member.builder().memberId("2526022361@kakao").build();
        City city = City.City(1L);
        String start = "2023-02-12";
        String end = "2023-02-19";
        PageRequest pageRequest = PageRequest.of(0, 10);

        GuestHouse guestHouse = GuestHouse.builder().guestHouseId(null).guestHouseName("1번 케이스").member(member).city(city).guestHouseTag("|감성|").build();
        Room room = Room.builder().roomId(null).roomName("1-1룸").guestHouse(guestHouse).roomStatus(RoomStatus.ROOM_ENABLE).build();
        guestHouse.setRooms(List.of(room));
        guestHouseRepository.save(guestHouse);


        RoomReservation roomReservation = RoomReservation.builder()
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .roomReservationStart(LocalDate.parse("2023-02-10"))
                .roomReservationEnd(LocalDate.parse("2023-02-13"))
                .guestHouse(guestHouse)
                .member(member)
                .build();

        roomReservationRepository.save(roomReservation);


        //where
        String[] tags = null;
        String sort = "default";

        Page<GuestHouse> guestHouseByFilter = guestHouseRepository.findGuestHouseByFilter(city.getCityId().intValue(), tags, start, end, pageRequest, sort);


        //then

        Assertions.assertEquals(true,roomReservationRepository.findAll().contains(roomReservation)); //예약이 잘 등록되어 있는지

        Assertions.assertEquals(false, guestHouseByFilter.getContent().stream().anyMatch(g -> {
            if (g.getGuestHouseId().equals(guestHouse.getGuestHouseId()))
                return true;
            return false;
        }));
    }


    @Test
    @DisplayName("메인필터 - 예약 가능 숙소 찾기 - 2번 케이스, 이미 예약 있는 룸")
    void getGuestHouseMainFilterTest3() {

        //given
        Member member = Member.builder().memberId("2526022361@kakao").build();
        City city = City.City(1L);
        String start = "2023-02-12";
        String end = "2023-02-19";
        PageRequest pageRequest = PageRequest.of(0, 10);

        GuestHouse guestHouse = GuestHouse.builder().guestHouseId(null).guestHouseName("1번 케이스").member(member).city(city).guestHouseTag("|감성|").build();
        Room room = Room.builder().roomId(null).roomName("1-1룸").guestHouse(guestHouse).roomStatus(RoomStatus.ROOM_ENABLE).build();
        guestHouse.setRooms(List.of(room));
        guestHouseRepository.save(guestHouse);


        RoomReservation roomReservation = RoomReservation.builder()
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .roomReservationStart(LocalDate.parse("2023-02-14"))
                .roomReservationEnd(LocalDate.parse("2023-02-16"))
                .guestHouse(guestHouse)
                .member(member)
                .build();

        roomReservationRepository.save(roomReservation);


        //where
        String[] tags = null;
        String sort = "default";

        Page<GuestHouse> guestHouseByFilter = guestHouseRepository.findGuestHouseByFilter(city.getCityId().intValue(), tags, start, end, pageRequest, sort);


        //then

        Assertions.assertEquals(true,roomReservationRepository.findAll().contains(roomReservation)); //예약이 잘 등록되어 있는지

        Assertions.assertEquals(false, guestHouseByFilter.getContent().stream().anyMatch(g -> {
            if (g.getGuestHouseId().equals(guestHouse.getGuestHouseId()))
                return true;
            return false;
        }));
    }


    @Test
    @DisplayName("메인필터 - 예약 가능 숙소 찾기 - 3번 케이스, 이미 예약 있는 룸")
    void getGuestHouseMainFilterTest4() {

        //given
        Member member = Member.builder().memberId("2526022361@kakao").build();
        City city = City.City(1L);
        String start = "2023-02-12";
        String end = "2023-02-19";
        PageRequest pageRequest = PageRequest.of(0, 10);

        GuestHouse guestHouse = GuestHouse.builder().guestHouseId(null).guestHouseName("1번 케이스").member(member).city(city).guestHouseTag("|감성|").build();
        Room room = Room.builder().roomId(null).roomName("1-1룸").guestHouse(guestHouse).roomStatus(RoomStatus.ROOM_ENABLE).build();
        guestHouse.setRooms(List.of(room));
        guestHouseRepository.save(guestHouse);


        RoomReservation roomReservation = RoomReservation.builder()
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .roomReservationStart(LocalDate.parse("2023-02-17"))
                .roomReservationEnd(LocalDate.parse("2023-02-22"))
                .guestHouse(guestHouse)
                .member(member)
                .build();

        roomReservationRepository.save(roomReservation);


        //where
        String[] tags = null;
        String sort = "default";

        Page<GuestHouse> guestHouseByFilter = guestHouseRepository.findGuestHouseByFilter(city.getCityId().intValue(), tags, start, end, pageRequest, sort);


        //then

        Assertions.assertEquals(true,roomReservationRepository.findAll().contains(roomReservation)); //예약이 잘 등록되어 있는지

        Assertions.assertEquals(false, guestHouseByFilter.getContent().stream().anyMatch(g -> {
            if (g.getGuestHouseId().equals(guestHouse.getGuestHouseId()))
                return true;
            return false;
        }));
    }


    @Test
    @DisplayName("메인필터 - 예약 가능 숙소 찾기 - 4번 케이스, 이미 예약 있는 룸")
    void getGuestHouseMainFilterTest5() {

        //given
        Member member = Member.builder().memberId("2526022361@kakao").build();
        City city = City.City(1L);
        String start = "2023-02-12";
        String end = "2023-02-19";
        PageRequest pageRequest = PageRequest.of(0, 10);

        GuestHouse guestHouse = GuestHouse.builder().guestHouseId(null).guestHouseName("1번 케이스").member(member).city(city).guestHouseTag("|감성|").build();
        Room room = Room.builder().roomId(null).roomName("1-1룸").guestHouse(guestHouse).roomStatus(RoomStatus.ROOM_ENABLE).build();
        guestHouse.setRooms(List.of(room));
        guestHouseRepository.save(guestHouse);


        RoomReservation roomReservation = RoomReservation.builder()
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .roomReservationStart(LocalDate.parse("2023-02-08"))
                .roomReservationEnd(LocalDate.parse("2023-02-24"))
                .guestHouse(guestHouse)
                .member(member)
                .build();

        roomReservationRepository.save(roomReservation);


        //where
        String[] tags = null;
        String sort = "default";

        Page<GuestHouse> guestHouseByFilter = guestHouseRepository.findGuestHouseByFilter(city.getCityId().intValue(), tags, start, end, pageRequest, sort);


        //then

        Assertions.assertEquals(true,roomReservationRepository.findAll().contains(roomReservation)); //예약이 잘 등록되어 있는지

        Assertions.assertEquals(false, guestHouseByFilter.getContent().stream().anyMatch(g -> {
            if (g.getGuestHouseId().equals(guestHouse.getGuestHouseId()))
                return true;
            return false;
        }));
    }


    @Test
    @DisplayName("메인필터 - 예약 검증 - 1,2,3 케이스 예약 형태가 존재하고 4번 형태의 예약을 시도할때 - 예약 불가해야함")
    void getGuestHouseMainFilterTest6() {

        //given
        Member member = Member.builder().memberId("2526022361@kakao").build();
        City city = City.City(1L);
        String start = "2023-02-12";
        String end = "2023-02-19";
        PageRequest pageRequest = PageRequest.of(0, 10);

        GuestHouse guestHouse = GuestHouse.builder().guestHouseId(null).guestHouseName("1번 케이스").member(member).city(city).guestHouseTag("|감성|").build();
        Room room = Room.builder().roomId(null).roomName("1-1룸").guestHouse(guestHouse).roomStatus(RoomStatus.ROOM_ENABLE).build();
        guestHouse.setRooms(List.of(room));
        guestHouseRepository.save(guestHouse);


        RoomReservation roomReservation = RoomReservation.builder()
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .roomReservationStart(LocalDate.parse("2023-02-08"))
                .roomReservationEnd(LocalDate.parse("2023-02-24"))
                .guestHouse(guestHouse)
                .member(member)
                .build();

        roomReservationRepository.save(roomReservation);


        //where
        String[] tags = null;
        String sort = "default";

        Page<GuestHouse> guestHouseByFilter = guestHouseRepository.findGuestHouseByFilter(city.getCityId().intValue(), tags, start, end, pageRequest, sort);


        //then

        Assertions.assertEquals(true,roomReservationRepository.findAll().contains(roomReservation)); //예약이 잘 등록되어 있는지

        Assertions.assertEquals(false, guestHouseByFilter.getContent().stream().anyMatch(g -> {
            if (g.getGuestHouseId().equals(guestHouse.getGuestHouseId()))
                return true;
            return false;
        }));
    }

}
