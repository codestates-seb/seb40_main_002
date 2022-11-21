package main.project.server.roomreservation.mapper;

import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.service.GuestHouseService;
import main.project.server.room.entity.Room;
import main.project.server.room.service.RoomService;
import main.project.server.roomreservation.dto.RoomReservationDto;
import main.project.server.roomreservation.entity.RoomReservation;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoomReservationMapper {

    RoomReservation roomReservationPostToRoomReservation(RoomReservationDto.Post roomReservationPost);
    default RoomReservationDto.Response reservationToReservationResponse(RoomReservation roomReservation,
                                                                         GuestHouseService guestHouseService,
                                                                         RoomService roomService) {

        GuestHouse guestHouse = guestHouseService.findGuestHouse(roomReservation.getGuestHouse().getGuestHouseId());
        Room room = roomService.findVerifiedRoom(roomReservation.getRoom().getRoomId());

        RoomReservationDto.Response response = RoomReservationDto.Response.builder()
                .guestHouseName(guestHouse.getGuestHouseName())
                .roomName(room.getRoomName())
                .roomImageUrl(room.getRoomImageUrl())
                .roomReservationStart(roomReservation.getRoomReservationStart())
                .roomReservationEnd(roomReservation.getRoomReservationEnd())
                .roomReservationStatus(roomReservation.getRoomReservationStatus())
                .build();
        return response;
    }

    default List<RoomReservationDto.Response> reservationsToReservationResponses(List<RoomReservation> roomReservations,
                                                                                 GuestHouseService guestHouseService,
                                                                                 RoomService roomService) {

        return roomReservations
                    .stream()
                    .map(reservation -> RoomReservationDto.Response
                            .builder()
                            .guestHouseName(guestHouseService.findGuestHouse(reservation.getGuestHouse().getGuestHouseId()).getGuestHouseName())
                            .roomName(roomService.findVerifiedRoom(reservation.getRoom().getRoomId()).getRoomName())
                            .roomImageUrl(reservation.getRoom().getRoomImageUrl())
                            .roomReservationStart(reservation.getRoomReservationStart())
                            .roomReservationEnd(reservation.getRoomReservationEnd())
                            .roomReservationStatus(reservation.getRoomReservationStatus())
                            .build())
                    .collect(Collectors.toList());
    }
}
