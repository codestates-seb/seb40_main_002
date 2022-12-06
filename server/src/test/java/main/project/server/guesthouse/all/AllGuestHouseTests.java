package main.project.server.guesthouse.all;


import com.google.gson.Gson;
import main.project.server.city.entity.City;
import main.project.server.guesthouse.dto.GuestHouseDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.entity.enums.GuestHouseStatus;
import main.project.server.guesthouse.repository.GuestHouseRepository;
import main.project.server.guesthouse.room.dto.RoomDto;
import main.project.server.guesthouse.room.entity.Room;
import main.project.server.guesthouse.room.entity.enums.RoomStatus;
import main.project.server.guesthouse.stub.MemberStub;
import main.project.server.member.controller.MemberController;
import main.project.server.member.dto.MemberDto;
import main.project.server.member.entity.Member;
import main.project.server.member.mapper.MemberMapper;
import main.project.server.member.repository.MemberRepository;
import main.project.server.review.entity.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class AllGuestHouseTests {

    @Autowired private MockMvc mvc;
    @Autowired
    MemberController memberController;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GuestHouseRepository guestHouseRepository;

    @Autowired
    Gson gson;

    @DisplayName("모든 게스트하우스 페이지네이션 - 모든 파라미터 X")
    @Test
    void allParametersNoting() throws Exception {

        //업주 멤버 하나 등록
        //업주 계정으로 게하 12개 등록
        //3개 게하는 도시 1, tag -> 감성, 야경, 아늑함
        //5개 게하는 도시 8, tag -> 시티뷰, 힐링, 대화
        //5개 게하는 도시 13, tag -> 신축, 공항근처, 놀거리
        //! 아무런 쿼리스트링 없이 잘 뽑히는지 (페이지네이션 여부 확인)


        //given
//        MemberDto.Post mockMemberDtoPost = MemberStub.getMockMemberDtoPost(0);
//
//        final String fileName = "1783_0.jpg"; //파일명
//        final String contentType = "jpg"; //파일타입
//        final String filePath = "C:\\Users\\wot00\\OneDrive\\바탕 화면\\메인 프로젝트\\" + fileName+"."+contentType; //파일경로
//        FileInputStream fileInputStream = new FileInputStream(filePath);
//
//        MockMultipartFile mockMultipartFile = new MockMultipartFile(
//                "images",fileName + "." + contentType,
//                contentType,
//                fileInputStream
//        );
//        createMember(mockMemberDtoPost, mockMultipartFile);


        Member member = MemberStub.getMockMember(0);
        memberRepository.save(member);
        registerGuestHouse(3,5,5, member);


        //when
        ResultActions actions = mvc.perform(get("/api/all-guesthouse"));


        //then

        actions.andExpect(jsonPath("$.pageInfo.totalElements", equalTo(13)));


//        actions.andExpect(jsonPath(jsonPath("$.pageInfo.totalElements", equalTo(13))));

    }


    void createMember(MemberDto.Post memberDto ,MockMultipartFile image) throws Exception {

        String content = gson.toJson(memberDto);

        mvc.perform(
                multipart("/api/members")
                        .file(image)
                        .content(content));
    }


    void registerGuestHouse(int c1, int c2, int c3, Member member) throws Exception {


        for (int i = 0; i < c1; i++) {

            final String fileName = "1783_0.jpg"; //파일명
            final String contentType = "jpg"; //파일타입
            final String filePath = "C:\\Users\\wot00\\OneDrive\\바탕 화면\\메인 프로젝트\\" + fileName+"."+contentType; //파일경로
            FileInputStream fileInputStream = new FileInputStream(filePath);


            MockMultipartFile guestHouseImage1 = new MockMultipartFile(
                    "guestHouseImage",fileName + "." + contentType,
                    contentType,
                    fileInputStream
            );

            MockMultipartFile guestHouseImage2 = new MockMultipartFile(
                    "guestHouseImage",fileName + "." + contentType,
                    contentType,
                    fileInputStream
            );


            MockMultipartFile roomImage1 = new MockMultipartFile(
                    "room-image",fileName + "." + contentType,
                    contentType,
                    fileInputStream
            );

            MockMultipartFile roomImage2 = new MockMultipartFile(
                    "room-image",fileName + "." + contentType,
                    contentType,
                    fileInputStream
            );



            GuestHouseDto.Post ghPost = GuestHouseDto.Post.builder()
                    .cityId(1L)
                    .guestHouseLocation("23.42323, 2323,2232")
                    .guestHouseInfo("좋은게하")
                    .guestHouseDetails(new Boolean[]{true, true, true, true, true, true, true, true, true, true})
                    .guestHousePhone("010-2323-1321")
                    .guestHouseName("좋은게하")
                    .guestHouseTag(new String[]{"감성", "야경", "아늑함"})
                    .guestHouseAddress(new String[]{"좋은", "동네", "입니다"})
                    .build();

            String ghPostJson = gson.toJson(ghPost);


            RoomDto.Post rPost = RoomDto.Post
                    .builder()
                    .roomInfo("좋은룸")
                    .roomName("매우 좋은 룸")
                    .roomPrice(40000)
                    .build();

            String rPostJson = gson.toJson(rPost);


            MultiValueMap multiValueMap = new LinkedMultiValueMap();
            multiValueMap.add("guest-house-dto", ghPostJson);
            multiValueMap.add("room-dto",rPostJson);

            ResultActions actions = mvc.perform(
                    multipart("/api/members")
                            .file(guestHouseImage1)
                            .file(guestHouseImage2)
                            .file(roomImage1)
                            .file(roomImage2)
                            .params(multiValueMap)
                            .contentType(MediaType.MULTIPART_MIXED)
                            .header("Authorization","eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJSb2xlcyI6WyJBRE1JTiJdLCJtZW1iZXJJZCI6IjEwMjc2NTY3MjUwODk1Njk0MzIzM0Bnb29nbGUiLCJzdWIiOiIxMDI3NjU2NzI1MDg5NTY5NDMyMzNAZ29vZ2xlIiwiaWF0IjoxNjcwMjcyOTg4LCJleHAiOjE2NzAyODAxODh9.FwVhiPq1TzZouZuMj4sZ4KHeaWVZh_V8gCsIIwgutRs"));


            actions.andExpect(status().isCreated());
        }


    }


//    void createGuestHouse(int c1, int c2, int c3, Member member) {
//
//
//        for (int i = 0; i < c1; i++) {
//
//            City city = City.City(1L);
//            GuestHouse guestHouse = GuestHouse.builder()
//                    .guestHouseId(null)
//                    .guestHouseName( i + "번 케이스")
//                    .member(member)
//                    .city(city)
//                    .guestHouseTag("|감성||아경||아늑함|")
//                    .guestHouseStatus(GuestHouseStatus.OPEN)
//                    .guestHouseStar(3.5f)
//                    .build();
//
//            Room room = Room.builder().roomId(null).roomName(i + " 룸").guestHouse(guestHouse).roomStatus(RoomStatus.ROOM_ENABLE).build();
//            guestHouse.setRooms(List.of(room));
//            guestHouseRepository.save(guestHouse);
//        }
//
//        for (int i = 0; i < c2; i++) {
//
//            City city = City.City(1L);
//            GuestHouse guestHouse = GuestHouse.builder()
//                    .guestHouseId(null)
//                    .guestHouseName( i + "번 케이스")
//                    .member(member)
//                    .city(city)
//                    .guestHouseTag("|시티뷰||힐링||대화|")
//                    .guestHouseStatus(GuestHouseStatus.OPEN)
//                    .guestHouseStar(2.4f)
//                    .build();
//
//            Room room = Room.builder().roomId(null).roomName(i + " 룸").guestHouse(guestHouse).roomStatus(RoomStatus.ROOM_ENABLE).build();
//            guestHouse.setRooms(List.of(room));
//            guestHouseRepository.save(guestHouse);
//        }
//
//        for (int i = 0; i < c3; i++) {
//
//            City city = City.City(1L);
//            GuestHouse guestHouse = GuestHouse.builder()
//                    .guestHouseId(null)
//                    .guestHouseName( i + "번 케이스")
//                    .member(member)
//                    .city(city)
//                    .guestHouseTag("|신축||공항근처||놀거리|")
//                    .guestHouseStatus(GuestHouseStatus.OPEN)
//                    .guestHouseStar(0f)
//                    .build();
//
//            Room room = Room.builder().roomId(null).roomName(i + " 룸").guestHouse(guestHouse).roomStatus(RoomStatus.ROOM_ENABLE).build();
//            guestHouse.setRooms(List.of(room));
//            guestHouseRepository.save(guestHouse);
//        }
//
//    }


}
