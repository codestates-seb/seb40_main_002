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

    }


    @Test
    @DisplayName("메인페이지 - 해당 날짜 예약 가능 숙소 찾기")
    void getGuestHouseMainFilterTest1() {



    }




}
