package main.project.server.guesthouse.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import main.project.server.dto.SingleResponseDto;
import main.project.server.guesthouse.dto.GuestHouseDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.mapper.GuestHouseMapper;
import main.project.server.guesthouse.service.GuestHouseService;
import main.project.server.guesthousedetails.dto.GuestHouseDetailsDto;
import main.project.server.guesthousedetails.entity.GuestHouseDetails;
import main.project.server.guesthousedetails.mapper.GuestHouseDetailsMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class GuestHouseController {
    private final GuestHouseService guestHouseService;
    private final GuestHouseMapper guestHouseMapper;
    private final GuestHouseDetailsMapper guestHouseDetailsMapper;


    @PostMapping(value = "/api/auth/guesthouse", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity postGuestHouse(@RequestPart(value = "guest-house-dto", required = false) @Valid GuestHouseDto.Post guestHouseDto,
                                         @RequestPart(value = "guest-house-details-dto", required = false) @Valid GuestHouseDetailsDto.Post guestHouseDetailsDto,
                                         @RequestPart(required = false) MultipartFile[] guestHouseImage,
                                         Principal principal
                                         ) throws IOException {

//        String memberId = principal.getName();

        //테스트, 삭제
        String memberId = "테스터";

        GuestHouseDetails guestHouseDetails = guestHouseDetailsMapper.guestHouseDetailsDtoPostToGuestHouseDetails(guestHouseDetailsDto);
        GuestHouse guestHouse = guestHouseMapper.guestHouseDtoPostToGuestHouse(guestHouseDto, memberId);
        guestHouseDetails.setGuestHouse(guestHouse);
        guestHouse.setGuestHouseDetails(guestHouseDetails);

        GuestHouse createdGuestHouse = guestHouseService.createGuestHouse(guestHouse, guestHouseImage);

        SingleResponseDto<GuestHouseDto.SingleGuestHouseResponse> singleResponseDto = new SingleResponseDto<>("created",null);
        return new ResponseEntity(singleResponseDto, HttpStatus.CREATED);
    }


    @PutMapping(value = "/api/auth/guesthouse/{guesthouse-id}")
    public ResponseEntity putGuestHouse(@RequestPart(value = "guest-house-dto", required = false) @Valid GuestHouseDto.Put guestHouseDto,
                                        @RequestPart(value = "guest-house-details-dto", required = false) @Valid GuestHouseDetailsDto.Put guestHouseDetailsDto,
                                        @RequestPart(required = false) MultipartFile[] guestHouseImage,
                                        Principal principal,
                                        @PathVariable("guesthouse-id") Long guestHouseId
                                        ) throws IOException {

//        String memberId = principal.getName();

        //테스트, 삭제
        String memberId = "테스터";

        GuestHouseDetails guestHouseDetails = guestHouseDetailsMapper.guestHouseDetailsDtoPutToGuestHouseDetails(guestHouseDetailsDto);
        GuestHouse guestHouse = guestHouseMapper.guestHouseDtoPutToGuestHouse(guestHouseDto, memberId);
        guestHouseDetails.setGuestHouse(guestHouse);
        guestHouse.setGuestHouseDetails(guestHouseDetails);

        guestHouse.setGuestHouseId(guestHouseId);
        guestHouseService.modifyGuestHouse(guestHouse, guestHouseImage);

        SingleResponseDto<GuestHouseDto.SingleGuestHouseResponse> singleResponseDto = new SingleResponseDto<>("modified",null);
        return new ResponseEntity(singleResponseDto, HttpStatus.OK);
    }


    @GetMapping("/api/auth/guesthouse/{guesthouse-id}")
    public ResponseEntity getGuestHouse(Principal principal,
                                        @PathVariable("guesthouse-id") Long guestHouseId){

        GuestHouse guestHouse = guestHouseService.findGuestHouse(guestHouseId);

        GuestHouseDto.SingleGuestHouseResponse singleGuestHouseResponse = guestHouseMapper.guestHouseToSingleGuestHouseResponse(guestHouse, guestHouseDetailsMapper);

        SingleResponseDto<GuestHouseDto.SingleGuestHouseResponse> singleResponseDto = new SingleResponseDto<>("find",singleGuestHouseResponse);
        return new ResponseEntity(singleResponseDto, HttpStatus.OK);
    }

}
