package main.project.server.review.repository;

import main.project.server.review.entity.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {

    // reviewId 기준 optional(null)한 reviewComment 조회
    Optional<ReviewComment> findByReviewReviewId(Long reviewId);
}
