package main.project.server.guesthouse.repository;


import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestHouseRepository extends JpaRepository<GuestHouse, Long> {

    Page<GuestHouse> findGuestHouseByMember(Member member, PageRequest pageRequest);
}
