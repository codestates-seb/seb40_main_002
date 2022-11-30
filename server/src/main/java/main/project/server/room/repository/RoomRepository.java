package main.project.server.room.repository;

import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.room.entity.Room;
import main.project.server.room.entity.enums.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByGuestHouseAndRoomStatus(GuestHouse guestHouse, RoomStatus roomStatus);

    List<Room> findByGuestHouseGuestHouseId(Long guestHouseId);



    @Query(
            value = "select * from room as r where r.guest_house_id = :guestHouseId and r.room_status = :roomStatus and r.room_id not in ( select rr.room_id from room_reservation as rr " +
                    "where ( rr.room_reservation_start <= :start and rr.room_reservation_end >= :start ) " +
                    "or ( rr.room_reservation_start >= :start and rr.room_reservation_end <= :end ) " +
                    "or ( rr.room_reservation_start <= :end and rr.room_reservation_end >= :end ))",
            nativeQuery = true
    )
    List<Room> findAllAvailableReservation(
            @Param("guestHouseId")Long guestHouseId,
            @Param("start")String start,
            @Param("end")String end,
            @Param("roomStatus")String roomStatus);
}
