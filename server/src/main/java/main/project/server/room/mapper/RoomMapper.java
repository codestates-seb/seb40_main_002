package main.project.server.room.mapper;

import main.project.server.room.dto.MultiRoomDto;
import main.project.server.room.dto.RoomDto;
import main.project.server.room.entity.Room;
import main.project.server.room.entity.enums.RoomStatus;
import main.project.server.room.repository.RoomRepository;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    default List<Room> roomPostsToRooms(MultiRoomDto<RoomDto.Post> roomPostDto) {
        return roomPostDto.getRoomDto()
                .stream()
                .map(roomPost -> Room
                        .builder()
                        .roomName(roomPost.getRoomName())
                        .roomInfo(roomPost.getRoomInfo())
                        .roomPrice(roomPost.getRoomPrice())
                        .roomStatus(RoomStatus.ROOM_ENABLE)
                        .build())
                .collect(Collectors.toList());
    }
    default List<List<Room>> roomPutsToRooms(MultiRoomDto<RoomDto.Put> roomPutDto) {
        List<List<Room>> lists = new ArrayList<>();


        List<Room> existRooms = roomPutDto.getRoomDto()
                .stream()
                .filter(RoomDto -> RoomDto.getRoomId() != null)
                .map(roomPut -> Room
                        .builder()
                        .roomId(roomPut.getRoomId())
                        .roomName(roomPut.getRoomName())
                        .roomInfo(roomPut.getRoomInfo())
                        .roomPrice(roomPut.getRoomPrice())
                        .roomStatus(RoomStatus.ROOM_ENABLE)
                        .build())
                .collect(Collectors.toList());
        lists.add(existRooms);

        List<Room> newRooms = roomPutDto.getRoomDto()
                .stream()
                .filter(RoomDto -> RoomDto.getRoomId() == null)
                .map(roomPut -> Room
                        .builder()
                        .roomName(roomPut.getRoomName())
                        .roomInfo(roomPut.getRoomInfo())
                        .roomPrice(roomPut.getRoomPrice())
                        .roomStatus(RoomStatus.ROOM_ENABLE)
                        .build())
                .collect(Collectors.toList());
        lists.add(newRooms);

        return lists;
    }

    List<RoomDto.Response> roomsToRoomResponses(List<Room> rooms);
}
