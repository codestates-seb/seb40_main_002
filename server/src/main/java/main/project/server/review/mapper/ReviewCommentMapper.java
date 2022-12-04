package main.project.server.review.mapper;

import main.project.server.review.dto.ReviewCommentDto;
import main.project.server.review.entity.ReviewComment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewCommentMapper {

    ReviewComment PostDtoToReviewComment(ReviewCommentDto.Post reviewPostDto);

    ReviewComment PutDtoToReviewComment(ReviewCommentDto.Put reviewPatchDto);

    ReviewCommentDto.Response reviewCommentToResponseDto(ReviewComment reviewComment);

}
