package main.project.server.statistics.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyReservationChartDto {

    private Long totalCount;
    private List<MonthlyReservation> monthlyReservationList = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class MonthlyReservation {

        private Integer month;
        private Long reservationCount;
    }
}
