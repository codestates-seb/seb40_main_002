package main.project.server.guesthouse.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


public class QueryStringDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MainFilterDto{

        private Integer cityId;

        @NotBlank
//        private String start = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        private String start;

        @NotBlank
        private String end;

        private String[] tag;

        private String sort;

        @Positive
        private int page = 1;

        @Positive
        private int size = 10;


    }
}
