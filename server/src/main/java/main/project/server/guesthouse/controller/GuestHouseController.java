package main.project.server.guesthouse.controller;

import lombok.RequiredArgsConstructor;
import main.project.server.annotation.dto.QueryStringArgResolver;
import main.project.server.dto.MultiResponseDto;
import main.project.server.dto.SingleResponseDto;
import main.project.server.guesthouse.dto.GuestHouseDto;
import main.project.server.guesthouse.dto.QueryStringDto;
import main.project.server.guesthouse.dto.ReserveStatisticsDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.mapper.GuestHouseMapper;
import main.project.server.guesthouse.service.GuestHouseService;
import main.project.server.review.dto.ReviewDto;
import main.project.server.review.mapper.ReviewMapper;
import main.project.server.review.service.ReviewService;
import main.project.server.guesthouse.room.dto.MultiRoomDto;
import main.project.server.guesthouse.room.dto.RoomDto;
import main.project.server.guesthouse.room.entity.Room;
import main.project.server.guesthouse.room.mapper.RoomMapper;
import main.project.server.tag.mapper.TagMapper;
import org.springframework.data.domain.Page;
import main.project.server.guesthouse.room.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class GuestHouseController {
    private final GuestHouseService guestHouseService;
    private final GuestHouseMapper guestHouseMapper;
    private final RoomService roomService;
    private final RoomMapper roomMapper;
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    private final TagMapper tagMapper;

    /** 업주가 게스트하우스를 등록하는 api **/
    @PostMapping(value = "/api/auth/guesthouse", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity postGuestHouse(@RequestPart(value = "guest-house-dto", required = false) @Valid GuestHouseDto.Post guestHouseDto,
                                         @RequestPart(required = false) MultipartFile[] guestHouseImage,
                                         @RequestPart(value = "room-dto") @Valid MultiRoomDto<RoomDto.Post> roomPostDtos,
                                         @RequestPart (value = "room-image", required = false) MultipartFile[] roomImages,
                                         @NotNull Principal principal) throws IOException {

        String memberId = principal.getName();

        GuestHouse guestHouse = guestHouseMapper.guestHouseDtoPostToGuestHouse(guestHouseDto, memberId, tagMapper);

        List<Room> rooms = roomMapper.roomPostsToRooms(roomPostDtos);

        guestHouseService.createGuestHouse(guestHouse, guestHouseImage, rooms, roomImages);

        SingleResponseDto<GuestHouseDto.response> singleResponseDto = new SingleResponseDto<>("created",null);
        return new ResponseEntity(singleResponseDto, HttpStatus.CREATED);
    }


    /** 업주가 자신의 게스트하우스의 내용을 업데이트 하는 api **/
    @PutMapping(value = "/api/auth/guesthouse/{guesthouse-id}")
    public ResponseEntity putGuestHouse(@RequestPart(value = "guest-house-dto", required = false) @Valid GuestHouseDto.Put guestHouseDto,
                                        @RequestPart(required = false) MultipartFile[] guestHouseImage,
                                        @RequestPart(value = "room-dto") @Valid MultiRoomDto<RoomDto.Put> roomPutDtos,
                                        @RequestPart (value = "room-image", required = false) MultipartFile[] roomImages,
                                        @RequestPart(value = "new-room-image", required = false) MultipartFile[] newRoomImages,
                                        @PathVariable("guesthouse-id") Long guestHouseId,
                                        @NotNull Principal principal) throws IOException {

        String memberId = principal.getName();

        GuestHouse guestHouse = guestHouseMapper.guestHouseDtoPutToGuestHouse(guestHouseDto, memberId, tagMapper);

        List<List<Room>> rooms = roomMapper.roomPutsToRooms(roomPutDtos);

        guestHouse.setGuestHouseId(guestHouseId);

        guestHouseService.modifyGuestHouse(guestHouse, guestHouseImage, memberId, rooms, roomImages, newRoomImages);

        SingleResponseDto<GuestHouseDto.response> singleResponseDto = new SingleResponseDto<>("modified",null);
        return new ResponseEntity(singleResponseDto, HttpStatus.OK);
    }


    /** 업주, 일반 회원이 볼 수 있는 게스트하우스의 상세내용 호출 api **/
    @GetMapping("/api/guesthouse/{guesthouse-id}")
    public ResponseEntity getGuestHouse(@PathVariable("guesthouse-id") @Positive Long guestHouseId,
                                        String start,
                                        String end)
    {
        GuestHouse guestHouse = guestHouseService.findGuestHouse(guestHouseId);

        // 호출 될 때 기본적으로 보여지는 리뷰(size=4)
        List<ReviewDto.Response> reviews = reviewMapper
                .reviewToReviewResponseDto(reviewService.getReviewPage(1, 4, guestHouseId).getContent());

        GuestHouseDto.response response = guestHouseMapper.
                guestHouseToSingleGuestHouseResponse(guestHouse, roomService, start, end, reviews, tagMapper);

        SingleResponseDto<GuestHouseDto.response> singleResponseDto = new SingleResponseDto<>("success", response);
        return new ResponseEntity(singleResponseDto, HttpStatus.OK);
    }


    /** 업주가 자신의 게스트하우스를 삭제(Close) 처리하는 api **/
    //게스트 하우스의 상태만 바꿈.
    @DeleteMapping("/api/auth/guesthouse/{guesthouse-id}")
    public ResponseEntity deleteGuestHouse(@NotNull Principal principal,
                                           @PathVariable("guesthouse-id") @Positive Long guestHouseId) {
        String memberId = principal.getName();

        guestHouseService.changeGuestHouseStatusAsClosed(guestHouseId, memberId);

        SingleResponseDto singleResponseDto = new SingleResponseDto("deleted", null) ;
        return new ResponseEntity(singleResponseDto, HttpStatus.OK);
    }

    /** 업주가 자신이 등록한 게스트하우스를 조회하는 게스트하우스의 페이지네이션 **/
    @GetMapping("/api/auth/members/{member-id}/guesthouse")
    public ResponseEntity getGuestHouseOfAdmin(@NotNull Principal principal,
                                               @PathVariable("member-id") String memberId,
                                               @RequestParam(name = "page", defaultValue = "1") @Positive Integer page,
                                               @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {

        String authMemberId = principal.getName();

        Page<GuestHouse> guestHousePage = guestHouseService.findGuestHouseByMember(authMemberId, page, size);

        List<GuestHouseDto.response> guestHouseResponseList = guestHouseMapper.
                guestHouseListToGuestHouseResponse(guestHousePage.getContent(), tagMapper, roomMapper);

        MultiResponseDto<GuestHouseDto.response> multiResponseDto = new MultiResponseDto<>("success",guestHouseResponseList,guestHousePage);

        return new ResponseEntity(multiResponseDto, HttpStatus.OK);
    }


    /** 메인페이지에서 필터를 적용(검색)하여 게스트하우스를 페이지네이션 api **/
    @GetMapping("/api/guesthouse")
    public ResponseEntity getGuestHouseMainFilter(Principal principal,
                                                  @QueryStringArgResolver @Valid QueryStringDto.MainFilterDto mainFilterDto) {

        Page<GuestHouse> guestHousePageByMainFilter = guestHouseService.findGuestHouseByMainFilter(mainFilterDto);

        List<GuestHouseDto.response> guestHouseResponseList = guestHouseMapper.
                guestHouseListToGuestHouseResponse(guestHousePageByMainFilter.getContent(), tagMapper, roomMapper);


        MultiResponseDto<GuestHouseDto.response> multiResponseDto = new MultiResponseDto<>("success",guestHouseResponseList, guestHousePageByMainFilter);
        return new ResponseEntity(multiResponseDto, HttpStatus.OK);
    }

    /** 태그만 적용 하여 메인페이지에 게스트하우스 페이지네이션을 보여 주는 api **/
    @GetMapping("/api/all-guesthouse")
    public ResponseEntity guestHouseAll(
            @RequestParam(name = "page", defaultValue = "1") @Positive Integer page,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size,
            @RequestParam(name = "sort", defaultValue = "default") String sort,
            @RequestParam(name = "tag", required = false) String[] tag //required를 false로 함으로써 null로 들어와도 차단되지 않는다.
    ) {


        Page<GuestHouse> guestHouseAll = guestHouseService.findAllGuestHouse(page, size, tag, sort);
        List<GuestHouseDto.response> guestHouseResponseLit = guestHouseMapper.guestHouseListToGuestHouseResponse(guestHouseAll.getContent(), tagMapper, roomMapper);
        MultiResponseDto<GuestHouseDto.response> multiResponseDto = new MultiResponseDto<>("success",guestHouseResponseLit, guestHouseAll);

        return new ResponseEntity(multiResponseDto, HttpStatus.OK);
    }

    /** 게스트하우스별 특정달의 일별 예약신청(결제) 통계 **/
    @GetMapping("/api/auth/chart/guesthouse/{guesthouse-id}/reserve-of-day")
    public ResponseEntity getChartOfGuestHouse(@PathVariable("guesthouse-id") Long guestHouseId,
                                               @RequestParam("yearmonth") String yearMonth,
                                               @NotNull Principal principal) {

        String memberId = principal.getName();

        List<ReserveStatisticsDto> allReserveChartOfCreatedAt = guestHouseService.findAllReserveChartOfCreatedAt(memberId, guestHouseId, yearMonth);

        return new ResponseEntity(allReserveChartOfCreatedAt, HttpStatus.OK);
    }
}
