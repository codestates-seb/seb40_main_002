package main.project.server.chart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.chart.dto.ChartDto;
import main.project.server.chart.service.ChartService;
import main.project.server.dto.SingleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChartController {

    private final ChartService chartService;

    @GetMapping("/api/auth/chart/{guest-house-id}")
    public ResponseEntity chart(@PathVariable("guest-house-id") Long guestHouseId,
                                @RequestParam Integer year,
                                Principal principal) {
        ChartDto result = chartService.monthlyReservationChart(guestHouseId, year, principal);
        return new ResponseEntity<>(new SingleResponseDto<>("get", result), HttpStatus.OK);
    }
}
