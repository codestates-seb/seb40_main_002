package main.project.server.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.dto.MultiResponseDto;
import main.project.server.dto.PageInfo;
import main.project.server.dto.SingleResponseDto;
import main.project.server.guesthouse.service.GuestHouseService;
import main.project.server.heart.dto.HeartDto;
import main.project.server.heart.entity.Heart;
import main.project.server.heart.mapper.HeartMapper;
import main.project.server.heart.service.HeartService;
import main.project.server.member.dto.MemberDto;
import main.project.server.member.entity.Member;
import main.project.server.member.mapper.MemberMapper;
import main.project.server.member.service.MemberService;
import main.project.server.review.dto.ReviewDto;
import main.project.server.review.entity.Review;
import main.project.server.review.mapper.ReviewMapper;
import main.project.server.review.service.ReviewService;
import main.project.server.room.service.RoomService;
import main.project.server.roomreservation.dto.RoomReservationDto;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.roomreservation.mapper.RoomReservationMapper;
import main.project.server.roomreservation.service.RoomReservationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final RoomReservationService reservationService;
    private final RoomReservationMapper reservationMapper;
    private final GuestHouseService guestHouseService;
    private final RoomService roomService;
    private final ReviewMapper reviewMapper;
    private final ReviewService reviewService;
    private final HeartService heartService;
    private final HeartMapper heartMapper;

    // 맴버 생성
    @PostMapping("/api/members")
    public ResponseEntity postMember(@RequestPart(value = "member-dto") @Valid MemberDto.Post memberPostDto,
                                     @RequestPart(required = false) MultipartFile memberImageFile){
        Member member = memberService.createMember(memberMapper.memberPostDtoToMember(memberPostDto), memberImageFile);
        return new ResponseEntity<>(new SingleResponseDto<>("created", memberMapper.memberToMemberResponseDto(member)), HttpStatus.CREATED);
    }


    // 맴버 정보 조회
    @GetMapping("/api/auth/members")
    public ResponseEntity getMember(Principal principal){
        Member member = memberService.findVerifiedMember(principal.getName());
        return new ResponseEntity<>(new SingleResponseDto<>("get ok", memberMapper.memberToMemberResponseDto(member)), HttpStatus.OK);
    }


    // 맴버 정보 수정
    @PatchMapping("/api/auth/members")
    public ResponseEntity patchMember(@RequestPart(value = "member-dto") MemberDto.Patch memberPatchDto,
                                      @RequestPart(required = false) MultipartFile memberImageFile,
                                      Principal principal){

        memberPatchDto.setMemberId(principal.getName());
        Member member = memberService.patchMember(memberMapper.memberPatchDtoToMember(memberPatchDto), memberImageFile);
        return new ResponseEntity<>(new SingleResponseDto<>("modified",memberMapper.memberToMemberResponseDto(member)) , HttpStatus.OK);
    }


    // 맴버 삭제
    @DeleteMapping("/api/auth/members")
    public ResponseEntity deleteMember(Principal principal){
        Member member = memberService.deleteMember(principal.getName());
        return new ResponseEntity<>(new SingleResponseDto<>("deleted", memberMapper.memberToMemberResponseDto(member)),HttpStatus.OK);
    }

    @PostMapping("/api/auth/members/logout")
    public ResponseEntity logoutMember(HttpServletRequest request) {

        String jws = request.getHeader("Authorization");
        memberService.registerLogoutToken(jws);
        SingleResponseDto<Object> singleResponseDto = new SingleResponseDto<>();
        singleResponseDto.setMessage("logout completed");
        return new ResponseEntity(singleResponseDto, HttpStatus.OK);
    }

    // 멤버 예약 정보
    @GetMapping("/api/auth/members/reservations")
    public ResponseEntity getMemberReservation(@Positive @RequestParam int page,
                                               @Positive @RequestParam int size,
                                               Principal principal) {

        Page<RoomReservation> reservationPage = reservationService.findMyReservation(principal, page - 1, size);
        PageInfo pageInfo = PageInfo.of(reservationPage);

        List<RoomReservationDto.Response> responses =
                reservationMapper.reservationsToReservationResponses(reservationPage.getContent(), guestHouseService, roomService);
        return new ResponseEntity<>(
                new MultiResponseDto<>("get", responses, pageInfo), HttpStatus.OK);

    }

    // 멤버 리뷰 조회
    @GetMapping("/api/auth/members/review")
    public ResponseEntity getReview(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "size", required = false, defaultValue = "4") int size,
                                    Principal principal) {

        Page<Review> reviewPage = reviewService.getReviewPageByMember(page, size, principal.getName());
        PageInfo pageInfo = PageInfo.of(reviewPage);
        List<ReviewDto.ResponseMyPage> responses = reviewMapper.reviewToReviewResponseMyPageDto(reviewPage.getContent());

        return new ResponseEntity<>(
                new MultiResponseDto<>("get ok", responses, pageInfo), HttpStatus.OK);
    }

    // 멤버 찜 조회
    @GetMapping("/api/auth/members/heart")
    public ResponseEntity getHeart(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                   @RequestParam(name = "size", required = false, defaultValue = "4") int size,
                                   Principal principal) {
        
        Page<Heart> heartPage = heartService.getHeartPageByMember(page, size, principal.getName());
        PageInfo pageInfo = PageInfo.of(heartPage);
        List<HeartDto.ResponseMyPage> responses = heartMapper.reviewToReviewResponseMyPageDto(heartPage.getContent());

        return new ResponseEntity<>(
                new MultiResponseDto<>("get ok", responses, pageInfo), HttpStatus.OK);
    }
}
