package main.project.server.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.dto.SingleResponseDto;
import main.project.server.review.dto.ReviewCommentDto;
import main.project.server.review.entity.ReviewComment;
import main.project.server.review.mapper.ReviewCommentMapper;
import main.project.server.review.service.ReviewCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.security.Principal;


@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class ReviewCommentController {

    private final ReviewCommentService reviewCommentService;
    private final ReviewCommentMapper reviewCommentMapper;

    // 리뷰 comment 생성
    @PostMapping("/api/auth/guesthouse/review/{review-id}/comment")
    public ResponseEntity postReviewComment(@RequestBody ReviewCommentDto.Post post,
                                            @PathVariable("review-id") @Positive Long reviewId,
                                            Principal principal) {

        ReviewComment reviewComment = reviewCommentService
                .postReviewComment(reviewCommentMapper.PostDtoToReviewComment(post), reviewId, principal);

        return new ResponseEntity<>(
                new SingleResponseDto<>("created",
                        reviewCommentMapper.reviewCommentToResponseDto(reviewComment)), HttpStatus.CREATED);
    }

    // 리뷰 comment 수정(put)
    @PutMapping("/api/auth/guesthouse/review/{review-id}/comment")
    public ResponseEntity patchReviewComment(@RequestBody ReviewCommentDto.Put put,
                                             @PathVariable("review-id") @Positive Long reviewId,
                                             Principal principal) {

        ReviewComment reviewComment = reviewCommentService
                .putReviewComment(reviewCommentMapper.PutDtoToReviewComment(put), reviewId, principal);

        return new ResponseEntity<>(
                new SingleResponseDto<>("put ok",
                        reviewCommentMapper.reviewCommentToResponseDto(reviewComment)), HttpStatus.OK);
    }

    // 리뷰 comment 조회
    @GetMapping("/api/guesthouse/review/{review-id}/comment")
    public ResponseEntity getReviewComment(@PathVariable("review-id") @Positive Long reviewId) {

        ReviewComment reviewComment = reviewCommentService.getReviewComment(reviewId);

        return new ResponseEntity<>(
                new SingleResponseDto<>("get ok",
                        reviewCommentMapper.reviewCommentToResponseDto(reviewComment)), HttpStatus.OK);
    }

    // 리뷰 comment 삭제
    @DeleteMapping("/api/auth/guesthouse/review/{review-id}/comment")
    public ResponseEntity deleteReview(@PathVariable("review-id") @Positive Long reviewId,
                                       Principal principal) {

        reviewCommentService.deleteReviewComment(reviewId, principal);

        return new ResponseEntity<>(
                new SingleResponseDto<>("deleted", null), HttpStatus.OK);
    }
}
