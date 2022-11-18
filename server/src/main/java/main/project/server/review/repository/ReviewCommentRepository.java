package main.project.server.review.repository;

import main.project.server.review.entity.Review;
import main.project.server.review.entity.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {

    Optional<ReviewComment> findByReviewReviewId(Long reviewId);
}
