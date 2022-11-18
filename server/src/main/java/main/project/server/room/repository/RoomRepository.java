package main.project.server.room.repository;

import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findByGuestHouse(GuestHouse guestHouse, Pageable pageable);
}
