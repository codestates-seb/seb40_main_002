package main.project.server.room.mapper;

import main.project.server.room.dto.RoomDto;
import main.project.server.room.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    Room roomPostToRoom(RoomDto.Post roomPostDto);
    Room roomPutToRoom(RoomDto.Put roomPutDto);
    RoomDto.Response roomToRoomResponse(Room room);
}
