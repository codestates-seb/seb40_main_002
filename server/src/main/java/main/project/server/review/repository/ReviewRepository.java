package main.project.server.review.repository;

import main.project.server.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 게하 기준 review Page
    Page<Review> findByGuestHouseGuestHouseId(Long guestHouseId, Pageable pageable);

    // 게하 기준 전체 리뷰
    List<Review> findByGuestHouseGuestHouseId(Long guestHouseId);

    // 멤버 기준 review Page(마이페이지)
    Page<Review> findByMemberMemberId(String memberId, Pageable pageable);
}
