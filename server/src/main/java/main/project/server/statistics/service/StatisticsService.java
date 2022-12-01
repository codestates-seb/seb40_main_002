package main.project.server.statistics.service;


import lombok.RequiredArgsConstructor;
import main.project.server.statistics.dto.ReserveCountOfGuestHouseDto;
import main.project.server.statistics.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    public List<ReserveCountOfGuestHouseDto> getPeriodChart(Integer[] period, Integer limit) {

        List<ReserveCountOfGuestHouseDto> chartOfGuestHouseForReserveCountInPeriod = statisticsRepository.getChartOfGuestHouseForReserveCountInPeriod(period, limit);

        return chartOfGuestHouseForReserveCountInPeriod;
    }
}
