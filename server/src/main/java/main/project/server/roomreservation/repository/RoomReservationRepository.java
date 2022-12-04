package main.project.server.roomreservation.repository;

import main.project.server.roomreservation.entity.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long>, CustomizedRoomReservationRepository {

}
