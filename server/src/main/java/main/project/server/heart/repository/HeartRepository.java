package main.project.server.heart.repository;

import main.project.server.heart.entity.Heart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByMemberMemberIdAndGuestHouseGuestHouseId(String memberId, Long guestHouseId);
    Page<Heart> findByMemberMemberId(String memberId, Pageable pageable);
}
