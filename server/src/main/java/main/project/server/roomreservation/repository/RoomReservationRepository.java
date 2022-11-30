package main.project.server.roomreservation.repository;

import main.project.server.chart.repository.CustomizedRoomReservationRepository;
import main.project.server.member.entity.Member;
import main.project.server.roomreservation.entity.RoomReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long>, CustomizedRoomReservationRepository {

}
