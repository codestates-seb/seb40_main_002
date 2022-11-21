package main.project.server.guesthouse.controller;

import lombok.RequiredArgsConstructor;
import main.project.server.annotation.QueryStringArgResolver;
import main.project.server.dto.MultiResponseDto;
import main.project.server.dto.SingleResponseDto;
import main.project.server.guesthouse.dto.GuestHouseDto;
import main.project.server.guesthouse.dto.QueryStringDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.mapper.GuestHouseMapper;
import main.project.server.guesthouse.service.GuestHouseService;
import main.project.server.guesthousedetails.dto.GuestHouseDetailsDto;
import main.project.server.guesthousedetails.entity.GuestHouseDetails;
import main.project.server.guesthousedetails.mapper.GuestHouseDetailsMapper;
import org.springframework.data.domain.Page;
import main.project.server.room.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
    private final GuestHouseDetailsMapper guestHouseDetailsMapper;

    private final RoomService roomService;


    /** 업주가 게스트하우스를 등록하는 api **/
    @PostMapping(value = "/api/auth/guesthouse", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity postGuestHouse(@RequestPart(value = "guest-house-dto", required = false) @Valid GuestHouseDto.Post guestHouseDto,
                                         @RequestPart(required = false) MultipartFile[] guestHouseImage,
                                         Principal principal
                                         ) throws IOException {

//        String memberId = principal.getName();

        String memberId = "업주";

        GuestHouse guestHouse = guestHouseMapper.guestHouseDtoPostToGuestHouse(guestHouseDto, memberId);


        GuestHouse createdGuestHouse = guestHouseService.createGuestHouse(guestHouse, guestHouseImage);

        SingleResponseDto<GuestHouseDto.response> singleResponseDto = new SingleResponseDto<>("created",null);
        return new ResponseEntity(singleResponseDto, HttpStatus.CREATED);
    }


    /** 업주가 자신의 게스트하우스의 내용을 업데이트 하는 api **/
    @PutMapping(value = "/api/auth/guesthouse/{guesthouse-id}")
    public ResponseEntity putGuestHouse(@RequestPart(value = "guest-house-dto", required = false) @Valid GuestHouseDto.Put guestHouseDto,
                                        @RequestPart(required = false) MultipartFile[] guestHouseImage,
                                        Principal principal,
                                        @PathVariable("guesthouse-id") Long guestHouseId
                                        ) throws IOException {

//        String memberId = principal.getName();

        String memberId = "업주";

        GuestHouse guestHouse = guestHouseMapper.guestHouseDtoPutToGuestHouse(guestHouseDto, memberId);

        guestHouse.setGuestHouseId(guestHouseId);
        guestHouseService.modifyGuestHouse(guestHouse, guestHouseImage, memberId);

        SingleResponseDto<GuestHouseDto.response> singleResponseDto = new SingleResponseDto<>("modified",null);
        return new ResponseEntity(singleResponseDto, HttpStatus.OK);
    }


    /** 업주, 일반 회원이 볼 수 있는 게스트하우스의 상세내용 호출 api **/
    @GetMapping("/api/guesthouse/{guesthouse-id}")
    public ResponseEntity getGuestHouse(Principal principal,
                                        @PathVariable("guesthouse-id") Long guestHouseId,
                                        @Positive @RequestParam int roomPage,
                                        @Positive @RequestParam int roomSize) {

        GuestHouse guestHouse = guestHouseService.findGuestHouse(guestHouseId);

        GuestHouseDto.response response = guestHouseMapper.
                guestHouseToSingleGuestHouseResponse(guestHouse, guestHouseDetailsMapper, roomService, roomPage - 1, roomSize);

        SingleResponseDto<GuestHouseDto.response> singleResponseDto = new SingleResponseDto<>("success", response);
        return new ResponseEntity(singleResponseDto, HttpStatus.OK);
    }


    /** 업주가 자신의 게스트하우스를 삭제(Close) 처리하는 api **/
    //게스트 하우스의 상태만 바꿈.
    @DeleteMapping("/api/auth/guesthouse/{guesthouse-id}")
    public ResponseEntity deleteGuestHouse(Principal principal,
                                           @PathVariable("guesthouse-id") @Positive Long guestHouseId) {

//        String memberId = principal.getName();

        String memberId = "업주";

        guestHouseService.changeGuestHouseStatusAsClosed(guestHouseId, memberId);

        SingleResponseDto singleResponseDto = new SingleResponseDto("deleted", null) ;
        return new ResponseEntity(singleResponseDto, HttpStatus.OK);
    }

    /** 업주가 자신이 등록한 게스트하우스를 조회하는 게스트하우스의 페이지네이션 **/
    @GetMapping("/api/auth/members/{member-id}/guesthouse")
    public ResponseEntity getGuestHouseOfAdmin(Principal principal,
                                               @PathVariable("member-id") String memberId,
                                               @RequestParam("page") @Positive Integer page,
                                               @RequestParam("size") @Positive Integer size) {



        String authMemberId = "업주";

        Page<GuestHouse> guestHousePage = guestHouseService.findGuestHouseByMember(authMemberId, page, size);

        List<GuestHouseDto.response> guestHouseResponseList = guestHouseMapper.
                guestHouseListToGuestHouseResponse(guestHousePage.getContent(), roomService, guestHouseDetailsMapper,page,size );

        MultiResponseDto<GuestHouseDto.response> multiResponseDto = new MultiResponseDto<>("success",guestHouseResponseList,guestHousePage);

        return new ResponseEntity(multiResponseDto, HttpStatus.OK);
    }


    @GetMapping("/api/guesthouse")
    public ResponseEntity getGuestHouseMainFilter(Principal principal,
                                                  @QueryStringArgResolver QueryStringDto.MainFilterDto mainFilterDto) {

        guestHouseService.findGuestHouseByFilter(mainFilterDto);

        return new ResponseEntity(HttpStatus.OK);
    }
}
