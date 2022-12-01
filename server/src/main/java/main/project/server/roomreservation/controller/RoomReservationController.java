package main.project.server.roomreservation.controller;

import lombok.RequiredArgsConstructor;
import main.project.server.dto.MultiResponseDto;
import main.project.server.dto.PageInfo;
import main.project.server.dto.SingleResponseDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.service.GuestHouseService;
import main.project.server.review.dto.ReviewDto;
import main.project.server.review.entity.Review;
import main.project.server.room.dto.RoomDto;
import main.project.server.room.entity.Room;
import main.project.server.room.service.RoomService;
import main.project.server.roomreservation.dto.RoomReservationDto;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.roomreservation.mapper.RoomReservationMapper;
import main.project.server.roomreservation.service.RoomReservationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/guesthouse/{guest-house-id}/room/{room-id}/reservation")
@Validated
public class RoomReservationController {

    private final RoomReservationService reservationService;

    private final RoomReservationMapper mapper;

    private final GuestHouseService guestHouseService;

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity postRoomReservation(@RequestBody @Valid RoomReservationDto.Post reservationPost,
                                              @PathVariable("guest-house-id") @Positive Long guestHouseId,
                                              @PathVariable("room-id") @Positive Long roomId,
                                              @NotNull Principal principal) throws IOException {
        RoomReservation roomReservation = mapper.roomReservationPostToRoomReservation(reservationPost);

        RoomReservation createdReservation =
                reservationService.createRoomReservation(roomReservation, guestHouseId, roomId, principal);
        RoomReservationDto.Response response =
                mapper.reservationToReservationResponse(createdReservation, guestHouseService, roomService);

        return new ResponseEntity<>(new SingleResponseDto<>("created", response), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{room-reservation-id}")
    public ResponseEntity deleteRoomReservation(@PathVariable("guest-house-id") @Positive Long guestHouseId,
                                                @PathVariable("room-id") @Positive Long roomId,
                                                @PathVariable("room-reservation-id") @Positive Long reservationId,
                                                Principal principal) {
        reservationService.deleteRoomReservation(guestHouseId, roomId, reservationId, principal);

        return new ResponseEntity<>(new SingleResponseDto<>("deleted", null), HttpStatus.OK);
    }
}
