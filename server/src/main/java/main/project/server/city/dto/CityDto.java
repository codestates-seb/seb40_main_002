package main.project.server.city.dto;


import lombok.*;


public class CityDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{

        private Integer cityId;

        private String cityName;

    }
}
