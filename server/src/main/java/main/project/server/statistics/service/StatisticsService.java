package main.project.server.statistics.service;


import lombok.RequiredArgsConstructor;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.service.GuestHouseService;
import main.project.server.roomreservation.repository.RoomReservationRepository;
import main.project.server.statistics.condition.SearchCondition;
import main.project.server.statistics.dto.AgeChartDto;
import main.project.server.statistics.dto.MonthlyReservationChartDto;
import main.project.server.statistics.dto.ReserveCountOfGuestHouseDto;
import main.project.server.statistics.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final RoomReservationRepository roomReservationRepository;
    private final GuestHouseService guestHouseService;

    public List<ReserveCountOfGuestHouseDto> getPeriodChart(Integer[] period, Integer limit) {

        List<ReserveCountOfGuestHouseDto> chartOfGuestHouseForReserveCountInPeriod = statisticsRepository.getChartOfGuestHouseForReserveCountInPeriod(period, limit);

        return chartOfGuestHouseForReserveCountInPeriod;
    }


    public MonthlyReservationChartDto getMonthlyReservationChart(Long guestHouseId, int year, Principal principal) {
        // guestHouse 존재 여부, 해당 member의 guestHouse 소유 여부 검증
        GuestHouse guestHouse = guestHouseService.verifyExistsGuestHouse(guestHouseId);
        guestHouseService.verifyOwnGuestHouse(guestHouse, principal.getName());

        // 통계 list 생성 후 month 순서로 정렬한 뒤, MonthlyReservationChartDto로 반환
        List<MonthlyReservationChartDto.MonthlyReservation> statistics = roomReservationRepository.findByYearGroupByMonth(guestHouseId, year);
        Collections.sort(statistics, new MonthComparator());
        return  MonthlyReservationChartDto.builder()
                .totalCount(statistics.stream().mapToLong(MonthlyReservationChartDto.MonthlyReservation::getReservationCount).sum())
                .monthlyReservationList(statistics)
                .build();
    }

    public AgeChartDto getAgeChart(Long guestHouseId, SearchCondition condition, Principal principal) {
        // guestHouse 존재 여부, 해당 member의 guestHouse 소유 여부 검증
//        GuestHouse guestHouse = guestHouseService.verifyExistsGuestHouse(guestHouseId);
//        guestHouseService.verifyOwnGuestHouse(guestHouse, principal.getName());

        // 통계 list 생성 후, AgeChartDto로 반환
        List<AgeChartDto.AgeGroupReservation> statistics = roomReservationRepository.findGroupByAge(guestHouseId, condition);
        return AgeChartDto.builder()
                .totalCount(statistics.stream().mapToLong(AgeChartDto.AgeGroupReservation::getCount).sum())
                .ageGroupReservationList(statistics)
                .build();
    }

    class MonthComparator implements Comparator<MonthlyReservationChartDto.MonthlyReservation> {
        @Override
        public int compare(MonthlyReservationChartDto.MonthlyReservation o1, MonthlyReservationChartDto.MonthlyReservation o2) {
            if (o1.getMonth() > o2.getMonth()) {
                return 1;
            } else if (o1.getMonth() < o2.getMonth()) {
                return -1;
            }
            return 0;
        }
    }
}
