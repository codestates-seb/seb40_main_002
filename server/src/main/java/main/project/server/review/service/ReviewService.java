package main.project.server.review.service;

import lombok.RequiredArgsConstructor;
import main.project.server.exception.BusinessException;
import main.project.server.exception.ExceptionCode;
import main.project.server.guesthouse.entity.GuestHouse;
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
    private final MemberService memberService;
    private final GuestHouseService guestHouseService;

    // 리뷰 등록
    public Review postReview(Review review, Long guestHouseId, Principal principal) {

        Member member = memberService.findVerifiedMember(principal.getName());  // principal 정보로 Member 조회
        GuestHouse guestHouse = guestHouseService.verifyExistsGuestHouse(guestHouseId); // guesthouseId 정보로 guesthouse 조회

        review.setMember(member);   // member 저장
        review.setGuestHouse(guestHouse);   // guesthouse 저장
        Review result = reviewRepository.save(review);  // 레포에 review 저장

        guestHouse.setGuestHouseStar(averageStar(guestHouseId));    // 리뷰 평점 평균 저장

        //게스트 하우스의 리뷰를 등록 했을 경우에 현재 리뷰 갯수에서 + 1
        guestHouse.setGuestHouseReviewCount(guestHouse.getGuestHouseReviewCount() + 1);

        return result;
    }

    // 리뷰 수정(put)
    public Review putReview(Review putReview, Long reviewId, Principal principal) {

        Review review = findVerifiedReview(reviewId);       // reviewId 정보로 review 조회
        Member member = memberService.findVerifiedMember(principal.getName());  // principal 정보로 Member 조회
        GuestHouse guestHouse = review.getGuestHouse();         // 이전 review 정보 중 guesthouse 불러옴

        verifyMemberConfirm(review, principal);         //  리뷰(수정 전)를 이전에 작성한 사람인지 확인

        putReview.setReviewId(reviewId);        // reviewId(원래 저장된 정보, 변하지 않는 값) 저장
        putReview.setMember(member);        // member(원래 저장된 정보, 변하지 않는 값) 저장
        putReview.setGuestHouse(guestHouse);        // guesthouse(원래 저장된 정보, 변하지 않는 값) 저장
        putReview.setCreatedAt(review.getCreatedAt());      // reviewComment 생성일 (원래 저장된 정보, 변하지 않는 값) 저장

        Review result = reviewRepository.save(review);      // 레포에 저장

        guestHouse.setGuestHouseStar(averageStar(guestHouse.getGuestHouseId()));    // 리뷰 평점 평균 저장

        return result;
    }

    // 리뷰 조회(페이지)
    public Page<Review> getReviewPage(int page, int size, Long guestHouseId){

        // pageable(페이지 구성 정보)를 생성 -> 페이지(page), 한 페이지에 몇개의 정보(size), reviewId 기준 내림차준(sort)
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("reviewId").descending());

        // 레포에서 존재하는(조회하는 게하 기준) 리뷰 페이지를 찾아 리턴
        return reviewRepository.findByGuestHouseGuestHouseId(guestHouseId, pageable);
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId, Principal principal){

        Review deleteReview = findVerifiedReview(reviewId);     // 삭제하고자 하는 review 조회
        verifyMemberConfirm(deleteReview, principal);   // review 작성자인지 판별
        GuestHouse guestHouse = deleteReview.getGuestHouse();   // 리뷰가 삭제될 guesthouse 조회

        //게스트 하우스의 리뷰를 삭제 했을 경우에 현재 리뷰 갯수에서 - 1
        deleteReview.getGuestHouse().setGuestHouseReviewCount(deleteReview.getGuestHouse().getGuestHouseReviewCount() - 1);

        reviewRepository.delete(deleteReview);  // 레포에서 리뷰 삭제
        guestHouse.setGuestHouseStar(averageStar(guestHouse.getGuestHouseId()));    // 리뷰 평점 평균 저장(삭제 반영)
    }

    // reviewId로 Optional(null) 하지 않은 review 조회
    public Review findVerifiedReview(Long reviewId) {

        Optional<Review> optionalReview = reviewRepository.findById(reviewId);  // 존재하지 않을 수도 있는 review 조회

        return optionalReview.orElseThrow(() -> new NoSuchElementException("No value present"));  // review null 일시 exception throw
    }

    // 사용자가 review 작성자인지 확인
    public void verifyMemberConfirm(Review review, Principal principal){

        if(!Objects.equals(principal.getName(), review.getMember().getMemberId())) {
            throw new BusinessException(ExceptionCode.NOT_A_REVIEW_WRITER);   // review 작성자가 아닐시 exception throw
        }
    }

    // 리뷰 평균 계산
    public Float averageStar(Long guestHouseId){

        // guestId 기준 모든 review를 list 형태로 조회
        List<Review> reviews = reviewRepository.findByGuestHouseGuestHouseId(guestHouseId);

        // list 형태의 review를 stream()을 이용해 평균 계산(기본값 :0.0f)
        return (float) reviews.stream().mapToDouble(Review::getStar).average().orElse(Double.NaN);
    }

    // 리뷰 멤버기준 조회(페이지)
    public Page<Review> getReviewPageByMember(int page, int size, String memberId){

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("reviewId").descending());

        return reviewRepository.findByMemberMemberId(memberId, pageable);   // memberId, pageable 기준으로 리뷰 조회
    }
}
