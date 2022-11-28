package main.project.server.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.dto.MultiResponseDto;
import main.project.server.dto.PageInfo;
import main.project.server.dto.SingleResponseDto;
import main.project.server.review.dto.ReviewDto;
import main.project.server.review.entity.Review;
import main.project.server.review.mapper.ReviewMapper;
import main.project.server.review.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    // 리뷰 생성
    @PostMapping("/api/auth/guesthouse/{guesthouse-id}/review")
    public ResponseEntity postReview(@RequestBody ReviewDto.Post post,
                                     @PathVariable("guesthouse-id") @Positive Long guestHouseId,
                                     Principal principal) {

        // 리뷰 생성 요청을 service 넘겨 처리하고 결과값으로 review 객체를 받음
        Review review = reviewService.postReview(reviewMapper.reviewPostDtoToReview(post), guestHouseId, principal);

        // 리뷰 생성 response
        return new ResponseEntity<>(
                new SingleResponseDto<>("created", reviewMapper.reviewToReviewResponseDto(review)),
                HttpStatus.CREATED);
    }

    // 리뷰 수정
    @PutMapping("/api/auth/guesthouse/review/{review-id}")
    public ResponseEntity patchReview(@RequestBody ReviewDto.Put put,
                                      @PathVariable("review-id") @Positive Long reviewId,
                                      Principal principal) {
        // 리뷰 수정 요청을 service 넘겨 처리하고 결과값으로 review 객체를 받음
        Review review = reviewService.putReview(reviewMapper.reviewPutDtoToReview(put), reviewId, principal);

        // 리뷰 수정 response
        return new ResponseEntity<>(
                new SingleResponseDto<>("put ok", reviewMapper.reviewToReviewResponseDto(review)),
                HttpStatus.OK);
    }

    // 리뷰 조회(페이지)
    @GetMapping("/api/guesthouse/{guesthouse-id}/review")
    public ResponseEntity getReview(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "size", required = false, defaultValue = "4") int size,
                                    @PathVariable("guesthouse-id") @Positive Long guestHouseId){

        // 리뷰 조회(페이지) 요청을 service 넘겨 처리하고 결과값으로 review로 구성된 Page를 받음
        Page<Review> reviewPage = reviewService.getReviewPage(page, size, guestHouseId);

        // Page 정보(page, size, totalpage, totalElements) 생성
        PageInfo pageInfo = PageInfo.of(reviewPage);

        // Page에서 .getContent()를 이용해 List 형태로 변환후 dto를 이용해 response 생성
        List<ReviewDto.Response> reviewResponseDto = reviewMapper.reviewToReviewResponseDto(reviewPage.getContent());

        return new ResponseEntity<>(
                new MultiResponseDto<>("get ok", reviewResponseDto, pageInfo), HttpStatus.OK);
    }

    // 리뷰 삭제
    @DeleteMapping("/api/auth/guesthouse/review/{review-id}")
    public ResponseEntity deleteReview(@PathVariable("review-id") @Positive Long reviewId,
                                       Principal principal) {

        reviewService.deleteReview(reviewId, principal);    // 리뷰 삭제

        return new ResponseEntity<>(
                new SingleResponseDto<>("deleted", null), HttpStatus.OK);
    }
}
