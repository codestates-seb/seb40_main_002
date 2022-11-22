package main.project.server.guesthouse.repository;


import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.member.entity.Member;
import main.project.server.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GuestHouseRepository extends JpaRepository<GuestHouse, Long> {
    Page<GuestHouse> findGuestHouseByMember(Member member, PageRequest pageRequest);

//    @Query(
//            value = "select gh.* from guest_house as gh left join room as ghr on gh.guest_house_id = ghr.guest_house_id " +
//                    "where gh.city_id = :cityId " +
//                    "and gh.guest_house_tag like :like " +
//                    "and (gh.guest_house_id, ghr.room_id) not in " +
//                    "( select ghrr.guest_house_id, ghrr.room_id from room_reservation as ghrr " +
//                    "where ghrr.room_reservation_start >= :start and ghrr.room_reservation_end <= :end ) group by guest_house_id",
//            countQuery = "select count(gh) from guest_house as gh left join room as ghr on gh.guest_house_id = ghr.guest_house_id " +
//                    "where gh.city_id = :cityId " +
//                    "and gh.guest_house_tag like :like " +
//                    "and (gh.guest_house_id, ghr.room_id) not in " +
//                    "( select ghrr.guest_house_id, ghrr.room_id from room_reservation as ghrr " +
//                    "where ghrr.room_reservation_start >= :start and ghrr.room_reservation_end <= :end ) group by guest_house_id",
//            nativeQuery = true
//    )
//    Page<GuestHouse> findGuestHouseByFilter(Integer cityId, String like, String start, String end, Pageable pageable);


//    @Query(
//            value = "select gh.* from guest_house as gh left join room as ghr on gh.guest_house_id = ghr.guest_house_id " +
//                    "where gh.city_id = :cityId " +
//                    "and gh.guest_house_tag like :like " +
//                    "and (gh.guest_house_id, ghr.room_id) not in " +
//                    "( select ghrr.guest_house_id, ghrr.room_id from room_reservation as ghrr " +
//                    "where ghrr.room_reservation_start >= :start and ghrr.room_reservation_end <= :end ) group by gh.guest_house_id",
//            countQuery = "select count( * ) from guest_house as gh left join room as ghr on gh.guest_house_id = ghr.guest_house_id " +
//                    "where gh.city_id = :cityId " +
//                    "and gh.guest_house_tag like :like " +
//                    "and (gh.guest_house_id, ghr.room_id) not in " +
//                    "( select ghrr.guest_house_id, ghrr.room_id from room_reservation as ghrr " +
//                    "where ghrr.room_reservation_start >= :start and ghrr.room_reservation_end <= :end ) group by gh.guest_house_id",
//            nativeQuery = true
//    )
//    Page<GuestHouse> findGuestHouseByFilter(Integer cityId, String like, String start, String end, Pageable pageable);


    @Query(
            value = "select gh.* from guest_house as gh join room as ghr on gh.guest_house_id = ghr.guest_house_id " +
                    "where gh.city_id = :cityId " +
                    "and gh.guest_house_tag like :like " +
                    "and ghr.room_status = 'ROOM_ENABLE' " +
                    "and (gh.guest_house_id, ghr.room_id) not in " +
                    "( select ghrr.guest_house_id, ghrr.room_id from room_reservation as ghrr where " +
                    "(ghrr.room_reservation_start <= :start AND ghrr.room_reservation_end >= :end ) OR " +
                    "(ghrr.room_reservation_start >= :start AND ghrr.room_reservation_end <= :end ) OR " +
                    "(ghrr.room_reservation_start <= :end AND ghrr.room_reservation_end >= :end )) GROUP BY gh.guest_house_id",
            countQuery = "select count( * ) from guest_house as gh left join room as ghr on gh.guest_house_id = ghr.guest_house_id " +
                    "where gh.city_id = :cityId " +
                    "and gh.guest_house_tag like :like " +
                    "and (gh.guest_house_id, ghr.room_id) not in " +
                    "( select ghrr.guest_house_id, ghrr.room_id from room_reservation as ghrr where " +
                    "(ghrr.room_reservation_start <= :start AND ghrr.room_reservation_end >= :end ) OR " +
                    "(ghrr.room_reservation_start >= :start AND ghrr.room_reservation_end <= :end ) OR " +
                    "(ghrr.room_reservation_start <= :end AND ghrr.room_reservation_end >= :end )) GROUP BY gh.guest_house_id",
            nativeQuery = true
    )
    Page<GuestHouse> findGuestHouseByFilter(Integer cityId, String like, String start, String end, Pageable pageable);



}
