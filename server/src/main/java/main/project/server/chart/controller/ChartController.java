package main.project.server.chart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.chart.condition.SearchCondition;
import main.project.server.chart.dto.AgeChartDto;
import main.project.server.chart.dto.MonthlyReservationChartDto;
import main.project.server.chart.service.ChartService;
import main.project.server.dto.SingleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChartController {

    private final ChartService chartService;

    // 월별 예약 통계 조회
    @GetMapping("/api/auth/monthly-chart/{guest-house-id}")
    public ResponseEntity monthlyChart(@PathVariable("guest-house-id") Long guestHouseId,
                                @RequestParam Integer year,
                                Principal principal) {
        MonthlyReservationChartDto result = chartService.getMonthlyReservationChart(guestHouseId, year, principal);
        return new ResponseEntity<>(new SingleResponseDto<>("get", result), HttpStatus.OK);
    }

    // 연령별 예약 통계 조답
    @GetMapping("/api/auth/age-chart/{guest-house-id}")
    public ResponseEntity ageChart(@PathVariable("guest-house-id") Long guestHouseId,
                                   @ModelAttribute SearchCondition condition,
                                   Principal principal) {
        AgeChartDto result = chartService.getAgeChart(guestHouseId, condition, principal);
        return new ResponseEntity<>(new SingleResponseDto<>("get", result), HttpStatus.OK);
    }
}
