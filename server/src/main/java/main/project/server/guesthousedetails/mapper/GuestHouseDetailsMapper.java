package main.project.server.guesthousedetails.mapper;

import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthousedetails.dto.GuestHouseDetailsDto;
import main.project.server.guesthousedetails.entity.GuestHouseDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestHouseDetailsMapper {

    GuestHouseDetails guestHouseDetailsDtoPostToGuestHouseDetails(GuestHouseDetailsDto.Post dto);
    GuestHouseDetails guestHouseDetailsDtoPutToGuestHouseDetails(GuestHouseDetailsDto.Put dto);

    GuestHouseDetailsDto.Response guestHouseDetailsToGuestHouseDetailsDtoResponse(GuestHouseDetails guestHouseDetails);
}
