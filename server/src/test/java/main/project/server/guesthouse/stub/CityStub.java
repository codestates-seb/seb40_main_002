package main.project.server.guesthouse.stub;

import main.project.server.city.entity.City;

import java.util.ArrayList;
import java.util.List;

public class CityStub {

    static List<City> cities = new ArrayList<>();

    static {

        cities = List.of(new City(1L, "서귀포"),
                new City(2L, "남원"),
                new City(3L, "표선"),
                new City(4L, "성산"),
                new City(5L, "구좌"),
                new City(6L, "조천"),
                new City(7L, "제주시"),
                new City(8L, "애월"),
                new City(9L, "한림"),
                new City(10L, "한경"),
                new City(11L, "대정"),
                new City(12L, "안덕"),
                new City(13L, "중문"));

    }


}
