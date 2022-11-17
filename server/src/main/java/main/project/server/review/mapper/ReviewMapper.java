package main.project.server.review.mapper;

import main.project.server.review.dto.ReviewDto;
import main.project.server.review.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review reviewPostDtoToReview(ReviewDto.Post reviewPostDto);
    Review reviewPutDtoToReview(ReviewDto.Put reviewPatchDto);
    ReviewDto.Response reviewToReviewResponseDto(Review review);

    List<ReviewDto.Response> reviewToReviewResponseDto(List<Review> reviews);
}
