package main.project.server.review.repository;

import main.project.server.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByGuestHouseGuestHouseId(Long guestHouseId, Pageable pageable);
    List<Review> findByGuestHouseGuestHouseId(Long guestHouseId);
}
