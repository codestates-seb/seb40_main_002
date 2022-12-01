package main.project.server.review.mapper;

import main.project.server.guesthouse.mapper.GuestHouseMapper;
import main.project.server.member.dto.MemberDto;
import main.project.server.member.entity.Member;
import main.project.server.review.dto.ReviewCommentDto;
import main.project.server.review.dto.ReviewDto;
import main.project.server.review.entity.Review;
import main.project.server.review.entity.ReviewComment;
import main.project.server.guesthouse.room.mapper.RoomMapper;
import main.project.server.roomreservation.dto.RoomReservationDto;
import main.project.server.tag.mapper.TagMapper;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review reviewPostDtoToReview(ReviewDto.Post reviewPostDto);

    Review reviewPutDtoToReview(ReviewDto.Put reviewPatchDto);

    List<ReviewDto.Response> reviewToReviewResponseDto(List<Review> reviews);   // 리뷰 조회(페이지)
    default List<ReviewDto.ResponseMyPage> reviewToReviewResponseMyPageDto(List<Review> reviews,
                                                                           GuestHouseMapper guestHouseMapper,
                                                                           RoomMapper roomMapper,
                                                                           TagMapper tagMapper)
    {
        if(reviews == null)
        {
            return null;
        }

        List<ReviewDto.ResponseMyPage> list = new ArrayList<>();

        for (Review review : reviews) {

            ReviewDto.ResponseMyPage responseMyPage = new ReviewDto.ResponseMyPage();
            responseMyPage.setReviewId(review.getReviewId());
            responseMyPage.setGuestHouse(guestHouseMapper.guestHouseToResponseSimple(review.getGuestHouse(), tagMapper));

            RoomReservationDto.ResponseSimple responseSimple = new RoomReservationDto.ResponseSimple();
            responseSimple.setRoom(roomMapper.roomToRoomDtoResponse(review.getRoomReservation().getRoom()));
            responseSimple.setRoomReservationStart(review.getRoomReservation().getRoomReservationStart());
            responseSimple.setRoomReservationEnd(review.getRoomReservation().getRoomReservationEnd());

            responseMyPage.setRoomReservation(responseSimple);
            responseMyPage.setComment(responseMyPage.getComment());
            responseMyPage.setStar(review.getStar());
            responseMyPage.setCreatedAt(review.getCreatedAt());
            responseMyPage.setModifiedAt(review.getModifiedAt());

            list.add(responseMyPage);
        }

        return list;
    }// 마이페이지 리뷰 조회


    default ReviewDto.Response reviewToReviewResponseDto(Review review){

        return ReviewDto.Response.builder()
                .reviewId(review.getReviewId())
                .comment(review.getComment())
                .star(review.getStar())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt())
                .member(memberToMemberResponseDto(review.getMember()))
                .guestHouseName(review.getGuestHouse().getGuestHouseName())
                .guestHouseMemberId(review.getGuestHouse().getMember().getMemberId())
                .reviewComment(reviewCommentToResponseDto(review.getReviewComment()))
                .build();
    }


    default MemberDto.Response memberToMemberResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.Response.ResponseBuilder response = MemberDto.Response.builder();

        response.memberId( member.getMemberId() );
        response.memberNickname( member.getMemberNickname() );
        response.memberEmail( member.getMemberEmail() );
        response.memberPhone( member.getMemberPhone() );
        response.memberBirth( member.getMemberBirth() );
        response.memberNationality( member.getMemberNationality() );
        response.memberRegisterKind( member.getMemberRegisterKind() );
        response.memberImageUrl( member.getMemberImageUrl() );

        return response.build();
    }

    default ReviewCommentDto.Response reviewCommentToResponseDto(ReviewComment reviewComment) {
        if ( reviewComment == null ) {
            return null;
        }

        ReviewCommentDto.Response response = new ReviewCommentDto.Response();

        response.setReviewCommentId( reviewComment.getReviewCommentId() );
        response.setReviewComment( reviewComment.getReviewComment() );
        response.setCreatedAt( reviewComment.getCreatedAt() );
        response.setModifiedAt( reviewComment.getModifiedAt() );

        return response;
    }
}
