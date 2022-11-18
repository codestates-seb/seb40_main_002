package main.project.server.roomreservation.service;

import lombok.RequiredArgsConstructor;
import main.project.server.room.entity.Room;
import main.project.server.room.entity.enums.RoomStatus;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import main.project.server.roomreservation.repository.RoomReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomReservationService {

    private final RoomReservationRepository roomReservationRepository;

    public RoomReservation createRoomReservation(RoomReservation roomReservation) {

        roomReservation.setRoomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE);
        return roomReservationRepository.save(roomReservation);
    }
}
