package main.project.server.guesthouseimage.mapper;

import main.project.server.guesthouseimage.dto.GuestHouseImageDto;
import main.project.server.guesthouseimage.entity.GuestHouseImage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestHouseImageMapper {

    GuestHouseImageDto guestHouseImageToGuestHouseImageDto(GuestHouseImage guestHouseImage);


    List<GuestHouseImageDto> guestHouseImageListToGuestHouseImageDtoList(List<GuestHouseImage> guestHouseImageList);
}
