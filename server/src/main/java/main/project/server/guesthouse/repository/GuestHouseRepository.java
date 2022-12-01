package main.project.server.guesthouse.repository;


import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GuestHouseRepository extends JpaRepository<GuestHouse, Long> {
    Page<GuestHouse> findGuestHouseByMember(Member member, PageRequest pageRequest);




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
    Page<GuestHouse> findGuestHouseByFilter(
            @Param("cityId")Integer cityId,
            @Param("like")String like,
            @Param("start")String start,
            @Param("end")String end,
            Pageable pageable);



    @Query(
            value = " select gh.* from guest_house as gh where gh.guest_house_tag like :tags",
            countQuery = " select count(*) from guest_house as gh where gh.guest_house_tag like :tags",
            nativeQuery = true
    )
    Page<GuestHouse> findAllGuestHouseOnlyAsTag(
            @Param("tags")String tags,
            Pageable pageable);




    //해당 쿼리가 작동되려면, 필드1 - int (ai) PK, 필드2 - varchar 가 존재하는 테이블이 존재해야한다. (필드2의 값음 1 ~ 31)
    @Query(
            value = " select day_of_month.dt, ifnull(reserv_statistics.reserve_count,0) from" +
                    " (" +
                    " select * from" +
                    " (select concat( :yearMonth , dc.day_date) as dt from day_cal as dc) as dc" +
                    " where substring(dc.dt,9,10) <= day(last_day(dc.dt))" +
                    " ) as day_of_month left join" +
                    " (" +
                    " select guest_house_id, date(create_at) as dt, count(*) as reserve_count" +
                    " from room_reservation as rr where rr.guest_house_id = :guestHouseId" +
                    " group by date(create_at) order by create_at" +
                    " ) as reserv_statistics on day_of_month.dt = reserv_statistics.dt"
                    ,
            nativeQuery = true
    )
    List<Object[]> getGuestHouseReserveStatistics(
            @Param("guestHouseId")Long guestHouseId,
            @Param("yearMonth")String yearMonth);
}
