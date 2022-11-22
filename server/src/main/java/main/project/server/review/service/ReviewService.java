package main.project.server.review.service;

import lombok.RequiredArgsConstructor;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.repository.GuestHouseRepository;
import main.project.server.guesthouse.service.GuestHouseService;
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
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final GuestHouseRepository guestHouseRepository;
    private final MemberService memberService;
    private final GuestHouseService guestHouseService;


    public Review postReview(Review review, Long guestHouseId, Principal principal) {

        Member member = memberService.findVerifiedMember(principal.getName());
        GuestHouse guestHouse = guestHouseService.verifyExistsGuestHouse(guestHouseId);

        review.setMember(member);
        review.setGuestHouse(guestHouse);
        Review result = reviewRepository.save(review);

        guestHouse.setGuestHouseStar(averageStar(guestHouseId));    // 리뷰 평점 평균 저장
        guestHouseRepository.save(guestHouse);

        return result;
    }


    public Review putReview(Review putReview, Long reviewId, Principal principal) {

        Review review = findVerifiedReview(reviewId);
        Member member = memberService.findVerifiedMember(principal.getName());
        GuestHouse guestHouse = review.getGuestHouse();
        verifyMemberConfirm(review, principal);

        putReview.setReviewId(reviewId);
        putReview.setMember(member);
        putReview.setGuestHouse(guestHouse);
        Review result = reviewRepository.save(review);

        guestHouse.setGuestHouseStar(averageStar(guestHouse.getGuestHouseId()));    // 리뷰 평점 평균 저장
        guestHouseRepository.save(guestHouse);

        return result;
    }


    public Page<Review> getReviewPage(int page, int size, Long guestHouseId){

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("reviewId").descending());

        return reviewRepository.findByGuestHouseGuestHouseId(guestHouseId, pageable);
    }


    public void deleteReview(Long reviewId, Principal principal){

        Review deleteReview = findVerifiedReview(reviewId);
        verifyMemberConfirm(deleteReview, principal);
        GuestHouse guestHouse = deleteReview.getGuestHouse();
        reviewRepository.delete(deleteReview);
        guestHouse.setGuestHouseStar(averageStar(guestHouse.getGuestHouseId()));    // 리뷰 평점 평균 저장(삭제 반영)
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

    // 리뷰 평균 계산
    public Float averageStar(Long guestHouseId){
        List<Review> reviews = reviewRepository.findByGuestHouseGuestHouseId(guestHouseId);
        float average = (float) reviews.stream().mapToDouble(s -> s.getStar()).average().orElse(Double.NaN);
        return average;
    }

    // 리뷰 멤버기준 조회(페이지)
    public Page<Review> getReviewPageByMember(int page, int size, String memberId){

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("reviewId").descending());

        return reviewRepository.findByMemberMemberId(memberId, pageable);
    }
}
