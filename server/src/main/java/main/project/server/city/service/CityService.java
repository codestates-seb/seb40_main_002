package main.project.server.city.service;

import lombok.RequiredArgsConstructor;
import main.project.server.city.entity.City;
import main.project.server.city.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;


    public List<City> findCities() {

        return cityRepository.findAll();
    }

}
