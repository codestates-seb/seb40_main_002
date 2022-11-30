package main.project.server.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.exception.BusinessException;
import main.project.server.exception.ExceptionCode;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.room.dto.RoomDto;
import main.project.server.room.entity.Room;
import main.project.server.room.entity.enums.RoomStatus;
import main.project.server.room.mapper.RoomMapper;
import main.project.server.room.repository.RoomRepository;
import main.project.server.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RoomService {

    @Value("${images.room-dir}")
    private String roomImageDir;

    @Value("${images.upload-ec2}")
    private String uploadEc2;

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    public void createRoom(List<Room> rooms, MultipartFile[] roomImages, Long guestHouseId) throws IOException {
        // 룸을 등록 안했을 경우는 리턴
        if (roomImageIsEmpty(rooms, roomImages)) {
            log.info("# Creating room & image not exist");
            return;
            // 들어온 룸 갯수와 이미지 갯수가 맞지 않는 경우 예외처리
        } else if (roomImageNotMatch(rooms, roomImages)) {
            log.error("# Request room & image count inconsistency");
            throw new BusinessException(ExceptionCode.ROOM_IMAGE_COUNT_INCONSISTENCY);
        }

        for (int i = 0; i < rooms.size(); i++) {
            rooms.get(i).addGuestHouse(GuestHouse.GuestHouse(guestHouseId));
            roomRepository.save(rooms.get(i));
            rooms.get(i).setRoomImageUrl(saveFile(roomImages[i], rooms.get(i).getRoomId()));
        }
    }

    public void updateRoom(List<List<Room>> rooms, MultipartFile[] roomImages, MultipartFile[] newRoomImages, Long guestHouseId) throws IOException {


        List<Room> findRooms = roomRepository.findByGuestHouseGuestHouseId(guestHouseId);
        List<Long> findRoomsId = findRooms.stream().map(Room::getRoomId).collect(Collectors.toList());

        // 기존 룸 요청 목록
        List<Room> existRooms = rooms.get(0);
        List<Long> findExistRoomsId = existRooms.stream().map(Room::getRoomId).collect(Collectors.toList());

        // 요청한 룸이 게스트하우스가 소유한 룸 id가 아닐 경우
        for (Long id : findExistRoomsId) {
            if (!findRoomsId.contains(id)) throw new BusinessException(ExceptionCode.NOT_MATCH_ROOM);
        }

        // 새로운 룸 요청 목록
        List<Room> newRooms = rooms.get(1);

        // 룸이 없을 경우는 리턴
        if (roomImageIsEmpty(existRooms, roomImages) && roomImageIsEmpty(newRooms, newRoomImages)
                && findRooms.size() == 0) {
            log.info("# Updating room & image not exist");
            return;
        }
        // 요청 들어온 룸 갯수와 이미지 갯수가 맞지 않는 경우 예외처리
        if (roomImageNotMatch(existRooms, roomImages) || roomImageNotMatch(newRooms, newRoomImages)) {
            log.error("# Request room & image inconsistency");
            throw new BusinessException(ExceptionCode.ROOM_IMAGE_COUNT_INCONSISTENCY);
        }
        // 기존 룸 수정
        int roomIdIdx = 0;
        for (int i = 0; i < findRooms.size(); i++) {
            Long roomId = findRoomsId.get(i);
            if (findExistRoomsId.contains(roomId)) {
                Room room = existRooms.get(roomIdIdx);
                deleteFile(findVerifiedRoom(roomId).getRoomImageUrl());
                room.setRoomImageUrl(saveFile(roomImages[roomIdIdx], roomId));
                room.addGuestHouse(GuestHouse.GuestHouse(guestHouseId));
                roomRepository.save(room);
                roomIdIdx++;
            } else {
                Room disabledRoom = findVerifiedRoom(roomId);
                disabledRoom.setRoomStatus(RoomStatus.ROOM_DISABLE);
                deleteFile(disabledRoom.getRoomImageUrl());
                roomRepository.save(disabledRoom);
            }
        }
        // 신규 룸 등록
        for (int i = 0; i < newRooms.size(); i++) {
            newRooms.get(i).addGuestHouse(GuestHouse.GuestHouse(guestHouseId));
            roomRepository.save(newRooms.get(i));
            newRooms.get(i).setRoomImageUrl(saveFile(newRoomImages[i], newRooms.get(i).getRoomId()));
        }
    }

    public Room findVerifiedRoom(Long roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        Room findRoom =
                optionalRoom.orElseThrow(() ->
                        new BusinessException(ExceptionCode.ROOM_NOT_FOUND));
        return findRoom;
    }

    public List<RoomDto.Response> getRoomResponses(Long guestHouseId) {
        GuestHouse guestHouse = GuestHouse.GuestHouse(guestHouseId);

        List<Room> rooms = roomRepository.findByGuestHouseAndRoomStatus(guestHouse, RoomStatus.ROOM_ENABLE);

        return roomMapper.roomsToRoomResponses(rooms);
    }


    private String saveFile(MultipartFile image, Long roomId) throws IOException {

        String uploadDir = roomImageDir + roomId;

        String path = uploadEc2 + "/" + uploadDir;

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        FileUtil.saveFile(path, fileName, image);

        String totalUrl = "/" + uploadDir + "/" + fileName;
        String imageUrl = totalUrl;

        return imageUrl;
    }

    private String deleteFile(String imageUrl) throws IOException {

        String url = imageUrl.substring(1);

        FileUtil.deleteFile(url);

        return imageUrl;
    }


    public List<RoomDto.Response> getReservePossibleOfRoomDtoResponseList(Long guestHouseId, List<Room> roomList, String start, String end) {

        List<RoomDto.Response> responses = roomMapper.roomsToRoomResponses(roomList); //모든 룸의 Response 리스트

        List<Room> allRoomAvailableReservation = roomRepository.findAllAvailableReservation( //예약 가능한 룸만 가져 온 리스트
                guestHouseId,
                start,
                end,
                RoomStatus.ROOM_ENABLE.toString());

        allRoomAvailableReservation.stream().forEach(resPossRoom -> {

            for (RoomDto.Response res : responses) {

                if (resPossRoom.getRoomId().equals(res.getRoomId())) { //예약 가능 룸의 id와 일치하다면
                    res.setReservePossible(true); //예약 가능 룸이므로 Response 안에 예약 가능 여부 필드를 true 할당
                }
            }
        });

        return responses;

    }

    private boolean roomImageIsEmpty(List<Room> rooms, MultipartFile[] roomImages) {
        return rooms.size() == 0 && roomImages == null;
    }

    private boolean roomImageNotMatch(List<Room> rooms, MultipartFile[] roomImages) {

        if (roomImages == null) {
            return !roomImageIsEmpty(rooms, roomImages)
                    && rooms.size() > 0;
        } else {
            return !roomImageIsEmpty(rooms, roomImages)
                    && (rooms.size() != roomImages.length
                    || Arrays.stream(roomImages).allMatch(i -> Objects.equals(i.getOriginalFilename(), "")));
        }
    }

}
