package main.project.server.review.service;

import lombok.RequiredArgsConstructor;
import main.project.server.member.entity.Member;
import main.project.server.member.service.MemberService;
import main.project.server.review.entity.Review;
import main.project.server.review.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberService memberService;


    public Review postReview(Review review, Principal principal) {

        Member member = memberService.findVerifiedMember(principal.getName());
        review.setMember(member);

        return reviewRepository.save(review);
    }


    public Review putReview(Review putReview, Long reviewId, Principal principal) {

        Review review = findVerifiedReview(reviewId);
        Member member = memberService.findVerifiedMember(principal.getName());
        verifyMemberConfirm(review, principal);
        putReview.setReviewId(reviewId);
        putReview.setMember(member);

        return reviewRepository.save(putReview);
    }


    public Page<Review> getReviewPage(int page, int size){

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("reviewId").descending());

        return reviewRepository.findAll(pageable);
    }


    public void deleteReview(Long reviewId, Principal principal){

        Review deleteReview = findVerifiedReview(reviewId);
        verifyMemberConfirm(deleteReview, principal);

        reviewRepository.delete(deleteReview);
    }


    public Review findVerifiedReview(Long reviewId) {

        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Review verifiedReview =
               optionalReview.orElseThrow(() -> new NoSuchElementException("No value present"));

        return verifiedReview;
    }

    public void verifyMemberConfirm(Review review, Principal principal){

        if(!Objects.equals(principal.getName(), review.getMember().getMemberId())) {
            // throw
        }
    }
}
