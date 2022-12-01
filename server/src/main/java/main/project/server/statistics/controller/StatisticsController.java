package main.project.server.statistics.controller;


import lombok.RequiredArgsConstructor;
import main.project.server.dto.SingleResponseDto;
import main.project.server.statistics.condition.SearchCondition;
import main.project.server.statistics.dto.AgeChartDto;
import main.project.server.statistics.dto.MonthlyReservationChartDto;
import main.project.server.statistics.dto.ReserveCountChartDto;
import main.project.server.statistics.dto.ReserveCountOfGuestHouseDto;
import main.project.server.statistics.service.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @GetMapping("/api/auth/monthly-chart/{guest-house-id}")
    public ResponseEntity monthlyChart(@PathVariable("guest-house-id") Long guestHouseId,
                                       @RequestParam Integer year,
                                       Principal principal) {
        MonthlyReservationChartDto result = service.getMonthlyReservationChart(guestHouseId, year, principal);
        return new ResponseEntity<>(new SingleResponseDto<>("get", result), HttpStatus.OK);
    }

    // 연령별 예약 통계 조답
    @GetMapping("/api/auth/age-chart/{guest-house-id}")
    public ResponseEntity ageChart(@PathVariable("guest-house-id") Long guestHouseId,
                                   @ModelAttribute SearchCondition condition,
                                   Principal principal) {
        AgeChartDto result = service.getAgeChart(guestHouseId, condition, principal);
        return new ResponseEntity<>(new SingleResponseDto<>("get", result), HttpStatus.OK);
    }
}
