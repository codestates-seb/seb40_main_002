package main.project.server.roomreservation.repository;

import main.project.server.member.entity.Member;
import main.project.server.roomreservation.entity.RoomReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    Page<RoomReservation> findByMember(Member member, Pageable pageable);
}
