package main.project.server.room.controller;

import lombok.RequiredArgsConstructor;
import main.project.server.dto.SingleResponseDto;
import main.project.server.room.dto.RoomDto;
import main.project.server.room.entity.Room;
import main.project.server.room.mapper.RoomMapper;
import main.project.server.room.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Validated
public class RoomController {

    private final RoomService roomService;

    private final RoomMapper mapper;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postRoom(@RequestPart("request") RoomDto.Post roomPost, @RequestPart("image") MultipartFile roomImageFile) throws IOException {
        // guestHouseId 받는 부분 추가할 것
        // principal.getName(); 룸이 속하는 숙소가 post하는 member 소유의 숙소id가 맞는지 확인할 것

        Room room = mapper.roomPostToRoom(roomPost);

        roomService.saveFile(roomImageFile);
        room.setRoomImageUrl(roomService.findFileUrl(roomImageFile));

        roomService.createRoom(room);

        return new ResponseEntity<>(new SingleResponseDto<>("created", null), HttpStatus.CREATED);

    }
}
