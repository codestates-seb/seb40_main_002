package main.project.server.chart.service;

import lombok.RequiredArgsConstructor;
import main.project.server.chart.dto.ChartDto;
import main.project.server.roomreservation.repository.RoomReservationRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final RoomReservationRepository roomReservationRepository;

    public ChartDto monthlyReservationChart(Long guestHouseId, int year, Principal principal) {
        // 해당 멤버 소유 숙소 맞는지 검증할 것

        List<ChartDto.MonthlyReservation> statistics = roomReservationRepository.findByYearGroupByMonth(guestHouseId, year);
        Collections.sort(statistics, new MonthComparator());

        return  ChartDto.builder()
                .totalCount(statistics.stream().mapToLong(ChartDto.MonthlyReservation::getReservationCount).sum())
                .monthlyReservationList(statistics)
                .build();
    }

    class MonthComparator implements Comparator<ChartDto.MonthlyReservation> {

        @Override
        public int compare(ChartDto.MonthlyReservation o1, ChartDto.MonthlyReservation o2) {
            if (o1.getMonth() > o2.getMonth()) {
                return 1;
            } else if (o1.getMonth() < o2.getMonth()) {
                return -1;
            }
            return 0;
        }
    }
}
