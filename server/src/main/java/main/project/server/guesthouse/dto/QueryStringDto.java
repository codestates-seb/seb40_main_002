package main.project.server.guesthouse.dto;


import lombok.*;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class QueryStringDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MainFilterDto{

        private Integer cityId;


//        @NotNull
//        private String start;
//
//
//        @NotNull
//        private String end;


        //체크인 시간이 안 들어올 경우 현재 코리안시간으로 초기화
        private String start = ZonedDateTime
                .now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        //체크인 시간이 안 들어올 경우 현재 코리안시간 + 1 로 초기화
        private String end = ZonedDateTime
                .now(ZoneId.of("Asia/Seoul"))
                .plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        private String[] tag;

        private String sort;

        private Integer page = 1;

        private Integer size = 10;

    }
}
