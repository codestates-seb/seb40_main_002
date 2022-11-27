package main.project.server.review.service;

import lombok.RequiredArgsConstructor;
import main.project.server.exception.BusinessException;
import main.project.server.exception.ExceptionCode;
import main.project.server.member.entity.Member;
import main.project.server.member.service.MemberService;
import main.project.server.review.entity.Review;
import main.project.server.review.entity.ReviewComment;
import main.project.server.review.repository.ReviewCommentRepository;
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

    // reviewComment 생성
    public ReviewComment postReviewComment(ReviewComment postReviewComment, Long reviewId, Principal principal) {

        if(reviewCommentRepository.findByReviewReviewId(reviewId).isEmpty()) {  // 리뷰 comment가 작성되어 있지 않으면 post(생성) 진행

            Review review = reviewService.findVerifiedReview(reviewId);     // comment할 review 조회
            Member member = memberService.findVerifiedMember(principal.getName());  // comment 생성할 member 조회

            postReviewComment.setReview(review);    // member 정보 저장
            postReviewComment.setMember(member);    // review 정보 저장

            return reviewCommentRepository.save(postReviewComment);     // 레포에 저장

        } else return putReviewComment(postReviewComment, reviewId, principal);     // 기존에 작성된 comment가 있다면 수정(put)으로 작업을 넘김
    }

    // reviewComment 수정(put)
    public ReviewComment putReviewComment(ReviewComment putReviewComment, Long reviewId, Principal principal) {

        Review review = reviewService.findVerifiedReview(reviewId);     // 리뷰 comment 수정할 review 조회
        Member member = memberService.findVerifiedMember(principal.getName());  // 리뷰 comment 수정할 member 조회

        verifyMemberConfirm(putReviewComment, principal);   // 수정할 review comment 작성자가 맞는지 확인

        putReviewComment.setReviewCommentId(findVerifiedReviewCommentByReviewId(reviewId).getReviewCommentId());    // reviewCommentId(원래 저장된 정보, 변하지 않는 값) 저장
        putReviewComment.setCreatedAt(findVerifiedReviewCommentByReviewId(reviewId).getCreatedAt());    // reviewComment 생성일 (원래 저장된 정보, 변하지 않는 값) 저장
        putReviewComment.setReview(review);     // review(원래 저장된 정보, 변하지 않는 값) 저장
        putReviewComment.setMember(member);     // member(원래 저장된 정보, 변하지 않는 값) 저장

        return reviewCommentRepository.save(putReviewComment);      // 수정내용 레포에 저장
    }

    // reviewComment 조회
    public ReviewComment getReviewComment(Long reviewId) {

        // reviewId 기준 존재하는 reviewComment(not null) 조회
        return findVerifiedReviewComment(findVerifiedReviewCommentByReviewId(reviewId).getReviewCommentId());
    }

    // reviewComment 삭제
    public void deleteReviewComment(Long reviewId, Principal principal){

        ReviewComment deleteReviewComment = findVerifiedReviewCommentByReviewId(reviewId);  // 삭제할 reviewComment 조회
        verifyMemberConfirm(deleteReviewComment, principal);    // 삭제할 reviewComment 작성자인지 확인

        reviewCommentRepository.delete(deleteReviewComment);    // 레포에서 reviewComment 삭제
    }

    // reviewComment 조회(not null)
    public ReviewComment findVerifiedReviewComment(Long reviewCommentId) {

        Optional<ReviewComment> optionalReviewComment = reviewCommentRepository.findById(reviewCommentId);

        return optionalReviewComment.orElseThrow(() -> new NoSuchElementException("No value present"));    // null일 경우 exception throw
    }

    // reviewComment 조회(reviewId 기준, not null)
    public ReviewComment findVerifiedReviewCommentByReviewId(Long reviewId) {

        Optional<ReviewComment> optionalReviewComment = reviewCommentRepository.findByReviewReviewId(reviewId);

        return optionalReviewComment.orElseThrow(() -> new NoSuchElementException("No value present"));    // null일 경우 exception throw
    }

    // reviewCommnet 작성한 사용자가 맞는지 확인
    public void verifyMemberConfirm(ReviewComment reviewComment, Principal principal){

        if(!Objects.equals(principal.getName(), reviewComment.getMember().getMemberId())) {
             throw new BusinessException(ExceptionCode.NOT_A_REVIEW_COMMENT_WRITER);    // 작성한 사용자가 아니면 exception throw
        }
    }
}
