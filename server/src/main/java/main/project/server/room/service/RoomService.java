package main.project.server.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.dto.SingleResponseDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.room.dto.RoomDto;
import main.project.server.room.entity.Room;
import main.project.server.room.entity.enums.RoomStatus;
import main.project.server.room.mapper.RoomMapper;
import main.project.server.room.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    public Room createRoom(Room room, long guestHouseId, Principal principal) {

        room.setRoomStatus(RoomStatus.ROOM_ENABLE);
        room.addGuestHouse(GuestHouse.GuestHouse(guestHouseId));
//        // 숙소 주인 맞는지 검사, 세팅
//        if (!room.getGuestHouse().getMember().getMemberId().equals(principal.getName()))
//            throw new RuntimeException();
        return roomRepository.save(room);
    }

    public Room updateRoom(Room room, long guestHouseId, Principal principal) {
        // 기존에 저장되어있던 파일 삭제 기능 추가할 것

//        if (room.getGuestHouse().getGuestHouseId() != guestHouseId
//                || !room.getGuestHouse().getMember().getMemberId().equals(principal.getName()))
//            throw new RuntimeException();
        room.setRoomStatus(RoomStatus.ROOM_ENABLE);
        room.addGuestHouse(GuestHouse.GuestHouse(guestHouseId));

        return roomRepository.save(room);
    }

    public Room deleteRoom(Long roomId, long guestHouseId, Principal principal) {
        Room findRoom = findVerifiedRoom(roomId);

//        if (findRoom.getGuestHouse().getGuestHouseId() != guestHouseId
//                || !findRoom.getGuestHouse().getMember().getMemberId().equals(principal.getName()))
//            throw new RuntimeException();

        findRoom.setRoomStatus(RoomStatus.ROOM_DISABLE);

        return roomRepository.save(findRoom);
    }

    public Room findVerifiedRoom(Long roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        Room findRoom =
                optionalRoom.orElseThrow(() ->
                        new RuntimeException());
        return findRoom;
    }

    public String findFileUrl(MultipartFile roomImageFile) {
        String fileUrl = "http://localhost:8080/images/" + roomImageFile.getOriginalFilename();
        log.info("fileUrl = {}", fileUrl);
        return fileUrl;
    }

    public void saveFile(MultipartFile roomImageFile) throws IOException {
        // MultipartFile.getOriginalFilename() : 업로드 파일명
        // MultipartFile.transferTo(...) : 파일 저장
        if (!roomImageFile.isEmpty()) {
            String fullPath = "images/" + roomImageFile.getOriginalFilename();

            Path path = Paths.get(fullPath).toAbsolutePath(); // 절대경로 사용

            log.info("filePath = {}", path);
            roomImageFile.transferTo(new File(String.valueOf(path)));
        }
    }

    public List<RoomDto.Response> getRoomResponses(long guestHouseId, int page, int size) {
        GuestHouse guestHouse = GuestHouse.GuestHouse(guestHouseId);

        Page<Room> pageRooms = roomRepository.findByGuestHouseAndRoomStatus(guestHouse, RoomStatus.ROOM_ENABLE, PageRequest.of(page, size,
                Sort.by("roomId").descending()));
        List<Room> rooms = pageRooms.getContent();

        return roomMapper.roomsToRoomResponses(rooms);
    }

}
