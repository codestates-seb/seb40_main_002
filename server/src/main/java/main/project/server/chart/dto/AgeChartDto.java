package main.project.server.chart.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgeChartDto {
    private Long totalCount;
    private List<AgeGroupReservation> ageGroupReservationList = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class AgeGroupReservation {

        private String ageGroup;
        private Long count;
    }
}
