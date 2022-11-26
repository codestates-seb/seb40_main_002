package main.project.server.city.mapper;


import main.project.server.city.dto.CityDto;
import main.project.server.city.entity.City;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityDto.Response cityToCityDtoResponse(City city);

    List<CityDto.Response> cityListToCityDtoResponseList(List<City> cityList);
}
