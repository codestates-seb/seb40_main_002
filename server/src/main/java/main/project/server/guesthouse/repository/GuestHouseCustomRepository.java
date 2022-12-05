package main.project.server.guesthouse.repository;

import main.project.server.guesthouse.entity.GuestHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestHouseCustomRepository {

    Page<GuestHouse> findGuestHouseByFilter(
            Integer cityId, String like, String start, String end, Pageable pageable);


    Page<GuestHouse>  findAllGuestHouse(
            String[] tags, Pageable pageable);
}
