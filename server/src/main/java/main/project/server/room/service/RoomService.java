package main.project.server.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.room.entity.Room;
import main.project.server.room.entity.enums.RoomStatus;
import main.project.server.room.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public Room createRoom(Room room) {
        // room id 검사 추가?

        room.setRoomStatus(RoomStatus.ROOM_ENABLE);
        return roomRepository.save(room);
    }

    public Room updateRoom(Room room) {
        Room findRoom = findVerifiedRoom(room.getRoomId());
//        findRoom.set
        findRoom.setPrice(room.getPrice());
        findRoom.setRoomImageUrl(room.getRoomImageUrl());

        return roomRepository.save(findRoom);
    }

    public Page<Room> findRooms(int page, int size) {
        return roomRepository.findAll(PageRequest.of(page, size,
                Sort.by("roomId").descending()));
    }

    public Room deleteRoom(Long roomId) {
        Room findRoom = findVerifiedRoom(roomId);
        findRoom.setRoomStatus(RoomStatus.ROOM_DISABLE);

        return roomRepository.save(findRoom);
    }

    private Room findVerifiedRoom(Long roomId) {
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

}
