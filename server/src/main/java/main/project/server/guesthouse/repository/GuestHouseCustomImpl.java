package main.project.server.guesthouse.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import main.project.server.guesthouse.entity.GuestHouse;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class GuestHouseCustomImpl implements GuestHouseCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<GuestHouse> findGuestHouseByFilter(Integer cityId, String like, String start, String end) {


        return null;
    }
}
