package main.project.server.room.mapper;

import main.project.server.room.dto.RoomDto;
import main.project.server.room.entity.Room;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    Room roomPostToRoom(RoomDto.Post roomPostDto);
    Room roomPutToRoom(RoomDto.Put roomPutDto);

    List<RoomDto.Response> roomsToRoomResponses(List<Room> rooms);
}
