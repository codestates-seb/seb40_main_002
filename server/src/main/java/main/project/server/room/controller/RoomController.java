package main.project.server.room.controller;

import lombok.RequiredArgsConstructor;
import main.project.server.dto.SingleResponseDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.member.service.MemberService;
import main.project.server.room.dto.RoomDto;
import main.project.server.room.entity.Room;
import main.project.server.room.mapper.RoomMapper;
import main.project.server.room.repository.RoomRepository;
import main.project.server.room.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/guesthouse/{guest-house-id}/room")
@Validated
public class RoomController {

    // principal.getName(); 룸이 속하는 숙소가 post하는 member 소유의 숙소id가 맞는지 확인할 것
    // guestHouse 연결

    private final RoomService roomService;

    private final RoomMapper mapper;

    private final MemberService memberService;

    private final RoomRepository roomRepository;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postRoom(@RequestPart("request") RoomDto.Post roomPost,
                                   @RequestPart("image") MultipartFile roomImageFile,
                                   @PathVariable("guest-house-id") long guestHouseId,
                                   Principal principal) throws IOException {
        Room room = mapper.roomPostToRoom(roomPost);

//        room.setGuestHouse(GuestHouse.builder().guestHouseId(guestHouseId).build());

        roomService.saveFile(roomImageFile);
        room.setRoomImageUrl(roomService.findFileUrl(roomImageFile));

        roomService.createRoom(room);

        return new ResponseEntity<>(new SingleResponseDto<>("created", null), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{room-id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity putRoom(@RequestPart("request") RoomDto.Put roomPut,
                                  @RequestPart("image") MultipartFile roomImageFile,
                                  @PathVariable("guest-house-id") long guestHouseId,
                                  @PathVariable("room-id") long roomId) throws IOException {
        roomPut.setRoomId(roomId);
        Room room = mapper.roomPutToRoom(roomPut);

        roomService.saveFile(roomImageFile);
        room.setRoomImageUrl(roomService.findFileUrl(roomImageFile));

        roomService.updateRoom(room);

        return new ResponseEntity<>(new SingleResponseDto<>("modified", null), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{room-id}")
    public ResponseEntity deleteRoom(@PathVariable("guest-house-id") long guestHouseId,
                                     @PathVariable("room-id") long roomId,
                                     Principal principal) {
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(new SingleResponseDto<>("deleted", null), HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity getRooms(@PathVariable("guest-house-id") long guestHouseId,
//                                   @Positive @RequestParam int page,
//                                   @Positive @RequestParam int size) {
//        // guestHouse 찾아서
//
//        PageRequest pageRequest = PageRequest.of(page, size);
//        Page<Room> pageRooms = roomRepository.findByGuestHouse(guestHouse, pageRequest);
//        List<Room> rooms = pageRooms.getContent();
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>("ok",mapper.roomsToRoomResponses(rooms)),
//                HttpStatus.OK);
//    }
}
