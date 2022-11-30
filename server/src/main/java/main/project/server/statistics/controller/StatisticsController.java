package main.project.server.statistics.controller;


import lombok.RequiredArgsConstructor;
import main.project.server.statistics.dto.ReserveCountChartDto;
import main.project.server.statistics.dto.ReserveCountOfGuestHouseDto;
import main.project.server.statistics.service.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StatisticsController {

    private final StatisticsService service;

    @GetMapping("/api/chart/period-reserve")
    public ResponseEntity periodChart(@RequestParam("yearMonthDay") Integer[] yearMonthDay,
                                      @RequestParam("limit")Integer limit) {


        List<ReserveCountOfGuestHouseDto> periodChart = service.getPeriodChart(yearMonthDay, limit);
        ReserveCountChartDto reserveCountChartDto = ReserveCountChartDto.of(periodChart);

        return new ResponseEntity(reserveCountChartDto, HttpStatus.OK);
    }
}
