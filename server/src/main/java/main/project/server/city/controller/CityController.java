package main.project.server.city.controller;

import lombok.RequiredArgsConstructor;
import main.project.server.city.dto.CityDto;
import main.project.server.city.entity.City;
import main.project.server.city.mapper.CityMapper;
import main.project.server.city.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    @GetMapping("/api/city")
    public ResponseEntity getCity() {

        List<City> cities = cityService.findCities();

        List<CityDto.Response> responses = cityMapper.cityListToCityDtoResponseList(cities);

        return new ResponseEntity(responses, HttpStatus.OK);

    }
}
