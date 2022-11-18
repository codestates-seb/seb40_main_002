package main.project.server.review.service;

import lombok.RequiredArgsConstructor;
import main.project.server.member.entity.Member;
import main.project.server.member.service.MemberService;
import main.project.server.review.entity.Review;
import main.project.server.review.entity.ReviewComment;
import main.project.server.review.repository.ReviewCommentRepository;
import main.project.server.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommentService {

    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewService reviewService;
    private final MemberService memberService;


    public ReviewComment postReviewComment(ReviewComment postReviewComment, Long reviewId, Principal principal) {
        if(reviewCommentRepository.findByReviewReviewId(reviewId).isEmpty()) {
            Review review = reviewService.findVerifiedReview(reviewId);
            Member member = memberService.findVerifiedMember(principal.getName());
            postReviewComment.setReview(review);
            postReviewComment.setMember(member);
            return reviewCommentRepository.save(postReviewComment);
        } else return putReviewComment(postReviewComment, reviewId, principal);
    }


    public ReviewComment putReviewComment(ReviewComment putReviewComment, Long reviewId, Principal principal) {

        Review review = reviewService.findVerifiedReview(reviewId);
        Member member = memberService.findVerifiedMember(principal.getName());
        verifyMemberConfirm(putReviewComment, principal);
        putReviewComment.setReviewCommentId(findVerifiedReviewCommentByReviewId(reviewId).getReviewCommentId());
        putReviewComment.setCreatedAt(findVerifiedReviewCommentByReviewId(reviewId).getCreatedAt());
        putReviewComment.setReview(review);
        putReviewComment.setMember(member);

        return reviewCommentRepository.save(putReviewComment);
    }

    public ReviewComment getReviewComment(Long reviewId) {

        ReviewComment reviewComment = findVerifiedReviewComment(findVerifiedReviewCommentByReviewId(reviewId).getReviewCommentId());

        return reviewComment;
    }


    public void deleteReviewComment(Long reviewId, Principal principal){

        ReviewComment deleteReviewComment = findVerifiedReviewCommentByReviewId(reviewId);
        verifyMemberConfirm(deleteReviewComment, principal);

        reviewCommentRepository.delete(deleteReviewComment);
    }


    public ReviewComment findVerifiedReviewComment(Long reviewCommentId) {

        Optional<ReviewComment> optionalReviewComment = reviewCommentRepository.findById(reviewCommentId);
        ReviewComment verifiedReviewComment =
                optionalReviewComment.orElseThrow(() -> new NoSuchElementException("No value present"));

        return verifiedReviewComment;
    }


    public ReviewComment findVerifiedReviewCommentByReviewId(Long reviewId) {

        Optional<ReviewComment> optionalReviewComment = reviewCommentRepository.findByReviewReviewId(reviewId);
        ReviewComment verifiedReviewComment =
                optionalReviewComment.orElseThrow(() -> new NoSuchElementException("No value present"));

        return verifiedReviewComment;
    }


    public void verifyMemberConfirm(ReviewComment reviewComment, Principal principal){

        if(!Objects.equals(principal.getName(), reviewComment.getMember().getMemberId())) {
            // throw
        }
    }
}
