package main.project.server.reservation;

import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.repository.GuestHouseRepository;
import main.project.server.member.entity.Member;
import main.project.server.member.repository.MemberRepository;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.roomreservation.repository.RoomReservationRepository;
import main.project.server.statistics.condition.SearchCondition;
import main.project.server.statistics.dto.AgeChartDto;
import main.project.server.statistics.dto.MonthlyReservationChartDto;
import main.project.server.stub.MemberStub;
import main.project.server.stub.ReservationStub;
import main.project.server.utils.DatabaseCleaner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void BeforeEach() {
        Member member1 = memberRepository.save(MemberStub.getAgeMember("1", "1995-01-01")); // 20대
        Member member2 = memberRepository.save(MemberStub.getAgeMember("2", "1985-01-01")); // 30대
        Member member3 = memberRepository.save(MemberStub.getAgeMember("3", "1975-01-01")); // 40대

        GuestHouse guestHouse = GuestHouse.builder().guestHouseName("테스트숙소").build();
        LocalDate givenReservationStart = LocalDate.parse("2022-01-01"); // 기준 - 2022년 1월
        guestHouseRepository.save(guestHouse);

        List<RoomReservation> givenReservations = List.of(
                ReservationStub.getCompletedMemberReservation(givenReservationStart, member1, guestHouse),
                ReservationStub.getCompletedMemberReservation(givenReservationStart, member2, guestHouse),
                ReservationStub.getCompletedMemberReservation(givenReservationStart, member3, guestHouse));

        roomReservationRepository.saveAll(givenReservations);
    }

    @AfterEach
    public void AfterEach() {
        databaseCleaner.execute();
    }

    @DisplayName("해당년도의 월별 예약현황 통계 테스트")
    @Test
    public void findByYearGroupByMonthTest() {
        // given

        // when
        List<MonthlyReservationChartDto.MonthlyReservation> result
                = roomReservationRepository.findByYearGroupByMonth(1L, 2022);

        System.out.println(result);

        // then
        assertEquals(1, result.size()); // 2022년 1월만 있음. size : 1
        assertEquals(1, result.get(0).getMonth()); // 1월

        assertEquals(3, result.get(0).getReservationCount()); // 3건
    }

    @DisplayName("연령별 예약현황 - year 기준 통계 테스트")
    @Test
    public void findGroupByAgeByYearTest() {
        // given
        SearchCondition givenYear = SearchCondition.builder().year(2022).build();

        // when
        List<AgeChartDto.AgeGroupReservation> yearResult
                = roomReservationRepository.findGroupByAge(1L, givenYear);

        System.out.println(yearResult);

        // then
        assertEquals(3, yearResult.size()); // 2022년

        assertEquals(1, yearResult.get(0).getCount()); // 연령별 1명씩
        assertEquals(1, yearResult.get(1).getCount());
        assertEquals(1, yearResult.get(2).getCount());
    }

    @DisplayName("연령별 예약현황 - month 기준 통계 테스트")
    @Test
    public void findGroupByAgeByMonthTest() {
        // given
        SearchCondition givenMonth = SearchCondition.builder().month(1).build();

        // when
        List<AgeChartDto.AgeGroupReservation> yearResult
                = roomReservationRepository.findGroupByAge(1L, givenMonth);
        System.out.println(yearResult);

        // then
        assertEquals(3, yearResult.size()); // 1월

        assertEquals(1, yearResult.get(0).getCount()); // 연령별 1명씩
        assertEquals(1, yearResult.get(1).getCount());
        assertEquals(1, yearResult.get(2).getCount());
    }

}
