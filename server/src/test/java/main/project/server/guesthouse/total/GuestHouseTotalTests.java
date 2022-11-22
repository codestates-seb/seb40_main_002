package main.project.server.guesthouse.total;


import main.project.server.city.entity.City;
import main.project.server.city.repository.CityRepository;
import main.project.server.guesthouse.dto.QueryStringDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.repository.GuestHouseRepository;
import main.project.server.guesthouse.service.GuestHouseService;
import main.project.server.member.entity.Member;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberRegisterKind;
import main.project.server.member.entity.enums.MemberStatus;
import main.project.server.member.repository.MemberRepository;
import main.project.server.room.entity.Room;
import main.project.server.room.repository.RoomRepository;
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
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ActiveProfiles("dev")
@Transactional
@SpringBootTest
public class GuestHouseTotalTests {

    City[] cities = new City[]{
            new City(1L,"제주시"),
            new City(2L,"조천"),
            new City(3L,"구좌"),
            new City(4L,"우도"),
            new City(5L,"성산"),
            new City(6L,"표선"),
            new City(7L,"남원")};

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
                    .memberRoles(List.of("USER","ADMIN")).build(),

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



    @Autowired
    GuestHouseService guestHouseService;


    @BeforeEach
    void beforeEach() {

//        InitCity();
        InitMember();

    }

    private void InitMember() {

        for (Member member : members) {
            memberRepository.save(member);
        }
    }

    private void InitCity() {
        for (City city : cities) {
            cityRepository.save(city);
        }
    }


    @Test
    @DisplayName("메인페이지 - 해당 날짜 예약 가능 숙소 찾기")
    void getGuestHouseMainFilterTest1() {

        //게스트 하우스 3개 등록
        //게스트 하우스 당 2개씩 룸 등록
        //레저베이션 두개 등록

        // ---- 게스트 하우스 세팅
        GuestHouse guestHouse1 = GuestHouse.builder()
                .guestHouseName("이보다더좋을수가게하1")
                .member(Member.Member("업주@google"))
                .city(City.City(1L))
                .guestHouseTag("|감성||눈|")
                .guestHouseInfo("과연 이곳을 오시겠습니까?")
                .build();
        GuestHouse guestHouse2 = GuestHouse.builder()
                .guestHouseName("이보다더좋을수가게하2")
                .member(Member.Member("업주@google"))
                .city(City.City(2L))
                .guestHouseTag("|감성||눈|")
                .guestHouseInfo("과연 이곳을 오시겠습니까?")
                .build();
        GuestHouse guestHouse3 = GuestHouse.builder()
                .guestHouseName("이보다더좋을수가게하3")
                .member(Member.Member("업주@google"))
                .city(City.City(1L))
                .guestHouseTag("|감성||눈|")
                .guestHouseInfo("과연 이곳을 오시겠습니까?")
                .build();

        GuestHouse save1 = guestHouseRepository.save(guestHouse1);
        GuestHouse save2 = guestHouseRepository.save(guestHouse2);
        GuestHouse save3 = guestHouseRepository.save(guestHouse3);

        // ---- 게스트 하우스 세팅



        // ---- 룸 세팅
        Room room1 = Room.builder().guestHouse(save1).roomName("좋은1").build();
        Room room2 = Room.builder().guestHouse(save1).roomName("좋은2").build();
        Room room3 = Room.builder().guestHouse(save1).roomName("좋은3").build();
        Room room4 = Room.builder().guestHouse(save2).roomName("좋은4").build();
        Room room5 = Room.builder().guestHouse(save3).roomName("좋은5").build();
        Room room6 = Room.builder().guestHouse(save3).roomName("좋은6").build();

        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        roomRepository.save(room4);
        roomRepository.save(room5);
        roomRepository.save(room6);

        // ---- 룸 세팅


        // ---- 예약 세팅

        String startStr = "2022-11-20";
        LocalDate startDate = LocalDate.parse(startStr);

        String endStr = "2022-11-27";
        LocalDate endDate = LocalDate.parse(endStr);


        RoomReservation reservation1 = RoomReservation.builder()
                .room(room1)
                .guestHouse(guestHouse1)
                .member(Member.Member("업주@google"))
                .roomReservationStart(startDate).roomReservationEnd(endDate)
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE).build();

        RoomReservation reservation2 = RoomReservation.builder()
                .room(room3)
                .guestHouse(guestHouse2)
                .member(Member.Member("업주@google"))
                .roomReservationStart(startDate).roomReservationEnd(endDate)
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE).build();

        roomReservationRepository.save(reservation1);
        roomReservationRepository.save(reservation2);

        // ---- 예약 세팅

//        QueryStringDto.MainFilterDto queryStringDto = QueryStringDto.MainFilterDto.builder()
//                .cityId(1)
//                .start("2022-11-17")
//                .end("2022-11-21")
//                .tag(new String[]{"감성","눈"})
//                .page(1)
//                .size(10)
//                .build();

//        Page<GuestHouse> result = guestHouseService.findGuestHouseByFilter(queryStringDto);
//        Assertions.assertEquals(2, result.getTotalElements());



                QueryStringDto.MainFilterDto queryStringDto = QueryStringDto.MainFilterDto.builder()
                .cityId(1)
                .start("2022-11-17")
                .end("2022-11-21")
                .tag(new String[]{"감성","눈"})
                .page(2)
                .size(10)
                .build();

        Page<GuestHouse> result = guestHouseService.findGuestHouseByMainFilter(queryStringDto);
        Assertions.assertEquals(0, result.getTotalElements());


    }




    //태그 널 경우

    //첨부 이미지가 없는 경우

    //각 데이터들 널일 경우
}
