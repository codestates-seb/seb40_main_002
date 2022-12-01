package main.project.server.heart.mapper;

import main.project.server.guesthouse.dto.GuestHouseDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.mapper.GuestHouseMapper;
import main.project.server.heart.dto.HeartDto;
import main.project.server.heart.entity.Heart;
import main.project.server.room.entity.Room;
import main.project.server.tag.mapper.TagMapper;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface HeartMapper {

    default List<HeartDto.ResponseMyPage> reviewToReviewResponseMyPageDto(List<Heart> hearts,
                                                                          TagMapper tagMapper,
                                                                          GuestHouseMapper guestHouseMapper){

        if(hearts == null)
        {
            return null;
        }

        List<HeartDto.ResponseMyPage> list = new ArrayList<>();

        for (Heart heart : hearts) {

            HeartDto.ResponseMyPage responseMyPage = new HeartDto.ResponseMyPage();
            responseMyPage.setHeartId(heart.getHeartId());

            GuestHouse heartGuestHouse = heart.getGuestHouse();

            GuestHouseDto.ResponseSimple responseSimple = guestHouseMapper.guestHouseToResponseSimple(heartGuestHouse, tagMapper);

            responseMyPage.setGuestHouse(responseSimple);
            responseMyPage.setCreatedAt(heart.getCreatedAt());
            responseMyPage.setModifiedAt(heart.getModifiedAt());

            list.add(responseMyPage);
        }

        return list;
    }
}
