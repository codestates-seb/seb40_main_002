package main.project.server.reservation;

import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.repository.GuestHouseRepository;
import main.project.server.member.entity.Member;
import main.project.server.member.repository.MemberRepository;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import main.project.server.roomreservation.repository.RoomReservationRepository;
import main.project.server.statistics.condition.SearchCondition;
import main.project.server.statistics.dto.AgeChartDto;
import main.project.server.statistics.dto.MonthlyReservationChartDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[IntegrationTest] RoomReservationRepository queryDSL test")
@ActiveProfiles("test")
@SpringBootTest
public class CustomizedRoomReservationRepositoryImplTest {

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private GuestHouseRepository guestHouseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    public void AfterEach() {
        roomReservationRepository.deleteAll();
        guestHouseRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("해당년도의 월별 예약현황 통계 테스트")
    @Test
    public void findByYearGroupByMonthTest() {
        // given
        GuestHouse guestHouse = GuestHouse.builder().guestHouseName("테스트숙소").build();
        LocalDate givenReservationStart = LocalDate.parse("2022-01-01");

        guestHouseRepository.save(guestHouse);

        RoomReservation givenReservation1 = RoomReservation.builder()
                .guestHouse(guestHouse)
                .roomReservationStart(givenReservationStart)
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .build();
        RoomReservation givenReservation2 = RoomReservation.builder()
                .guestHouse(guestHouse)
                .roomReservationStart(givenReservationStart)
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .build();

        roomReservationRepository.save(givenReservation1);
        roomReservationRepository.save(givenReservation2);

        // when
        List<MonthlyReservationChartDto.MonthlyReservation> result
                = roomReservationRepository.findByYearGroupByMonth(guestHouse.getGuestHouseId(), 2022);

        System.out.println(result);

        // then
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getMonth());
        assertEquals(2, result.get(0).getReservationCount());
    }

    @DisplayName("게스트하우스의 연령별 예약현황 통계 테스트")
    @Test
    public void findGroupByAgeTest() {
        // given

        Member member1 = Member.builder().memberId("1").memberBirth("1995-01-01").build(); // 20대
        Member member2 = Member.builder().memberId("2").memberBirth("1985-01-01").build(); // 30대
        Member member3 = Member.builder().memberId("3").memberBirth("1975-01-01").build(); // 40대

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        GuestHouse guestHouse = GuestHouse.builder().guestHouseName("테스트숙소").build();
        LocalDate givenReservationStart1 = LocalDate.parse("2022-01-01"); // 기준 - 2022년 1월
        LocalDate givenReservationStart2 = LocalDate.parse("2023-01-01"); // 2023년
        LocalDate givenReservationStart3 = LocalDate.parse("2022-02-01"); // 2월

        guestHouseRepository.save(guestHouse);

        RoomReservation givenReservation1 = RoomReservation.builder()
                .roomReservationStart(givenReservationStart1)
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .member(member1)
                .guestHouse(guestHouse)
                .build();
        RoomReservation givenReservation2 = RoomReservation.builder()
                .roomReservationStart(givenReservationStart2)
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .member(member2)
                .guestHouse(guestHouse)
                .build();
        RoomReservation givenReservation3 = RoomReservation.builder()
                .roomReservationStart(givenReservationStart3)
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .member(member3)
                .guestHouse(guestHouse)
                .build();

        roomReservationRepository.save(givenReservation1);
        roomReservationRepository.save(givenReservation2);
        roomReservationRepository.save(givenReservation3);

        SearchCondition year = SearchCondition.builder().year(2022).build();
        SearchCondition month = SearchCondition.builder().month(1).build();
        SearchCondition all = SearchCondition.builder().year(2022).month(1).build();
        SearchCondition nothing = SearchCondition.builder().build();

        // when
        List<AgeChartDto.AgeGroupReservation> yearResult
                = roomReservationRepository.findGroupByAge(guestHouse.getGuestHouseId(), year);
        List<AgeChartDto.AgeGroupReservation> monthResult
                = roomReservationRepository.findGroupByAge(guestHouse.getGuestHouseId(), month);
        List<AgeChartDto.AgeGroupReservation> allResult
                = roomReservationRepository.findGroupByAge(guestHouse.getGuestHouseId(), all);
        List<AgeChartDto.AgeGroupReservation> nothingResult
                = roomReservationRepository.findGroupByAge(guestHouse.getGuestHouseId(), nothing);

        System.out.println(yearResult);
        System.out.println(monthResult);
        System.out.println(allResult);
        System.out.println(nothingResult);

        // then
        assertEquals(2, yearResult.size()); // 2022년
        assertEquals(2, monthResult.size()); // 1월
        assertEquals(1, allResult.size()); // 2022년, 1월
        assertEquals(3, nothingResult.size()); // 전체

        assertEquals(1, nothingResult.get(0).getCount()); // 연령별 1명씩
        assertEquals(1, nothingResult.get(1).getCount());
        assertEquals(1, nothingResult.get(2).getCount());
    }
}
