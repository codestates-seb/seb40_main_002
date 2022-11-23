package main.project.server.guesthouse.dto;


import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class QueryStringDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MainFilterDto{

        @NotNull
        @Positive
        private Integer cityId;

        @NotBlank
//        private String start = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        private String start;

        @NotBlank
        private String end;

        private String[] tag;

        @NotBlank
        private String sort;

        @NotNull
        @Positive
        private Integer page;

        @NotNull
        @Positive
        private Integer size;


    }
}
