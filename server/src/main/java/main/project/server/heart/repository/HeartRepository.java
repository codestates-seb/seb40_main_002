package main.project.server.heart.repository;

import main.project.server.heart.entity.Heart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    // 사용자와 게하 기준으로 조회
    Optional<Heart> findByMemberMemberIdAndGuestHouseGuestHouseId(String memberId, Long guestHouseId);

    // 사용자 기준 찜하기 상태가 true인 페이지 조회
    Page<Heart> findByMemberMemberIdAndHeartStatusIs(String memberId, Boolean bool, Pageable pageable);
}
