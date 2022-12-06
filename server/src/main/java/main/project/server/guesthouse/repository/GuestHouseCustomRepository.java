package main.project.server.guesthouse.repository;

import com.querydsl.core.types.OrderSpecifier;
import main.project.server.guesthouse.entity.GuestHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GuestHouseCustomRepository {

    Page<GuestHouse> findGuestHouseByFilter(
            Integer cityId, String[] tags, String start, String end, Pageable pageable, String sort);


    Page<GuestHouse> findAllGuestHouse(
            String[] tags, Pageable pageable, String sort);
}
