package main.project.server.guesthouse.guesthouseimage.mapper;

import main.project.server.guesthouse.guesthouseimage.dto.GuestHouseImageDto;
import main.project.server.guesthouse.guesthouseimage.entity.GuestHouseImage;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface GuestHouseImageMapper {
    GuestHouseImageDto guestHouseImageToGuestHouseImageDto(GuestHouseImage guestHouseImage);
}
