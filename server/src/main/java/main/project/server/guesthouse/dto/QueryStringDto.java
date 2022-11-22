package main.project.server.guesthouse.dto;


import lombok.*;

public class QueryStringDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MainFilterDto{

        private Integer cityId;
        private String start;
        private String end;
        private String[] tag;
        private String sort;
        private Integer page;
        private Integer size;


    }
}
