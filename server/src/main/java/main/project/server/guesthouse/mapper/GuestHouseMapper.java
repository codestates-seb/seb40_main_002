package main.project.server.guesthouse.mapper;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import main.project.server.city.entity.City;
import main.project.server.guesthouse.dto.GuestHouseDto;
import main.project.server.guesthouse.dto.ReserveStatisticsDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.entity.enums.GuestHouseStatus;
import main.project.server.guesthousedetails.entity.GuestHouseDetails;

import main.project.server.member.entity.Member;
import main.project.server.review.dto.ReviewDto;
import main.project.server.room.service.RoomService;

import main.project.server.tag.mapper.TagMapper;
import org.mapstruct.Mapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;



@Mapper(componentModel = "spring")
public interface GuestHouseMapper {


    default public GuestHouse guestHouseDtoPostToGuestHouse(GuestHouseDto.Post dto, String memberId, TagMapper tagMapper){


        GuestHouse guestHouse = GuestHouse.builder()
                .guestHouseName(dto.getGuestHouseName())
                .member(Member.Member(memberId))
                .city(City.City(dto.getCityId()))
                .guestHouseLocation(dto.getGuestHouseLocation())
                .guestHouseAddress(addressArrayToAddressStr(dto.getGuestHouseAddress()))
                .guestHousePhone(dto.getGuestHousePhone())
                .guestHouseStatus(GuestHouseStatus.OPEN)
                .guestHouseDetails(booleanArrayToGuestHouseDetails(dto.getGuestHouseDetails()))
                .guestHouseTag(tagMapper.createSortedTagString(dto.getGuestHouseTag()))
                .guestHouseInfo(dto.getGuestHouseInfo())
                .guestHouseReviewCount(0L) //최초 등록시 초기화
                .guestHouseStar(0f) //최초 등록시 초기화
                .build();

        guestHouse.getGuestHouseDetails().setGuestHouse(guestHouse);

        return guestHouse;

    }

    /** 문자열 배열 형태의 주소를 DB에 저장하는 문자열 형태로 변환하는 메소드 **/
    default String addressArrayToAddressStr(String[] addressArr) {

        StringBuilder stringBuilder = new StringBuilder();

        IntStream.range(0, addressArr.length)
                .forEach(index-> {
                    stringBuilder.append(addressArr[index]);
                    if (index != addressArr.length - 1) {
                        stringBuilder.append(",");
                    }
                });

        return stringBuilder.toString();
    }




    default public GuestHouse guestHouseDtoPutToGuestHouse(GuestHouseDto.Put dto, String memberId, TagMapper tagMapper){

        GuestHouse guestHouse = GuestHouse.builder()
                .guestHouseName(dto.getGuestHouseName())
                .member(Member.Member(memberId))
                .city(City.City(dto.getCityId()))
                .guestHouseLocation(dto.getGuestHouseLocation())
                .guestHouseAddress(addressArrayToAddressStr(dto.getGuestHouseAddress()))
                .guestHousePhone(dto.getGuestHousePhone())
                .guestHouseStatus(GuestHouseStatus.OPEN)
                .guestHouseDetails(booleanArrayToGuestHouseDetails(dto.getGuestHouseDetails()))
                .guestHouseTag(tagMapper.createSortedTagString(dto.getGuestHouseTag()))
                .guestHouseInfo(dto.getGuestHouseInfo())
                .build();

        guestHouse.getGuestHouseDetails().setGuestHouse(guestHouse);

        return guestHouse;
    }



    default List<GuestHouseDto.response> guestHouseListToGuestHouseResponse(
            List<GuestHouse> guestHouseList,
            TagMapper tagMapper) {

        return guestHouseList.stream().map(guestHouse -> {

            return GuestHouseDto.response.builder()
                    .guestHouseId(guestHouse.getGuestHouseId())
                    .guestHouseName(guestHouse.getGuestHouseName())
                    .memberId(guestHouse.getMember().getMemberId())
                    .memberNickname(guestHouse.getMember().getMemberNickname())
                    .memberPhone(guestHouse.getMember().getMemberPhone())
                    .memberImageUrl(guestHouse.getMember().getMemberImageUrl())
                    .guestHouseLocation(guestHouse.getGuestHouseLocation())
                    .guestHouseAddress(addressStrToAddressArr(guestHouse.getGuestHouseAddress()))
                    .guestHousePhone(guestHouse.getGuestHousePhone())
                    .guestHouseStatus(guestHouse.getGuestHouseStatus())
                    .guestHouseDetails(guestHouseDetailsToBooleanArray(guestHouse.getGuestHouseDetails()))
                    .guestHouseStar(guestHouse.getGuestHouseStar())
                    .guestHouseTag(tagMapper.createSortedTagArray(guestHouse.getGuestHouseTag()))
                    .guestHouseImage(guestHouse.guestHouseImageListToUrlList())
                    .guestHouseInfo(guestHouse.getGuestHouseInfo())
                    .guestHouseReviewCount(guestHouse.getGuestHouseReviewCount())
                    .createdAt(guestHouse.getCreatedAt().toString())
                    .modifiedAt(guestHouse.getModifiedAt().toString())
                    .build();

        }).collect(Collectors.toList());
    }

    default GuestHouseDetails booleanArrayToGuestHouseDetails(Boolean[] option) {

        return GuestHouseDetails.builder()
                .guestHouseKitchen(option[0])
                .guestHouseOcean(option[1])
                .guestHouseBoard(option[2])
                .guestHouseWashing(option[3])
                .guestHouseEssential(option[4])
                .guestHouseTask(option[5])
                .guestHouseCook(option[6])
                .guestHouseWifi(option[7])
                .guestHouseParking(option[8])
                .guestHouseParty(option[9])
                .build();
    }


    default List<ReserveStatisticsDto> ObjectArrayToReserveStatisticsDtoList(List<Object[]> objectsList) {

        if(objectsList == null)
            return null;


        List<ReserveStatisticsDto> reserveStatisticsDtoList = new ArrayList<>();

        for (Object[] objArr : objectsList) {

            String date = (String)objArr[0];
            BigInteger reserveRegisterCtn = (BigInteger)objArr[1];

            ReserveStatisticsDto reserveStatisticsDto = ReserveStatisticsDto.builder()
                    .dt(date)
                    .reserveCount(reserveRegisterCtn)
                    .build();

            reserveStatisticsDtoList.add(reserveStatisticsDto);
        }
        return reserveStatisticsDtoList;
    }




    default GuestHouseDto.response guestHouseToSingleGuestHouseResponse(

            GuestHouse guestHouse,
            RoomService roomService,
            String start,
            String end,
            List<ReviewDto.Response> reviews,
            TagMapper tagMapper) {

        Member adminMember = guestHouse.getMember();

        return GuestHouseDto.response.builder()
                .guestHouseId(guestHouse.getGuestHouseId())
                .guestHouseName(guestHouse.getGuestHouseName())
                .memberId(adminMember.getMemberId())
                .memberNickname(adminMember.getMemberNickname())
                .memberPhone(adminMember.getMemberPhone())
                .memberImageUrl(adminMember.getMemberImageUrl())
                .guestHouseLocation(guestHouse.getGuestHouseLocation())
                .guestHouseAddress(addressStrToAddressArr(guestHouse.getGuestHouseAddress()))
                .guestHousePhone(guestHouse.getGuestHousePhone())
                .guestHouseStatus(guestHouse.getGuestHouseStatus())
                .guestHouseDetails(guestHouseDetailsToBooleanArray(guestHouse.getGuestHouseDetails()))
                .guestHouseStar(guestHouse.getGuestHouseStar())
                .guestHouseTag(tagMapper.createSortedTagArray(guestHouse.getGuestHouseTag()))
                .guestHouseImage(guestHouse.guestHouseImageListToUrlList()) //리스트, 처리 필요
                .guestHouseInfo(guestHouse.getGuestHouseInfo())
                .rooms(roomService.getReservePossibleOfRoomDtoResponseList(guestHouse.getGuestHouseId(), guestHouse.getRooms(), start, end)) //리스트, 처리 필요
                .guestHouseReviewCount(guestHouse.getGuestHouseReviewCount())
                .reviews(reviews)   // 리뷰
                .createdAt(guestHouse.getCreatedAt().toString())
                .modifiedAt(guestHouse.getModifiedAt().toString())
                .build();
    }

    default String[] addressStrToAddressArr(String address) {

        if (address != null) {
            String[] addressArr = address.split(",");
            return addressArr;
        }

        return new String[0];
    }

    default Boolean[] guestHouseDetailsToBooleanArray(GuestHouseDetails guestHouseDetails) {

        return new Boolean[]{
                guestHouseDetails.getGuestHouseKitchen(),
                guestHouseDetails.getGuestHouseOcean(),
                guestHouseDetails.getGuestHouseBoard(),
                guestHouseDetails.getGuestHouseWashing(),
                guestHouseDetails.getGuestHouseEssential(),
                guestHouseDetails.getGuestHouseTask(),
                guestHouseDetails.getGuestHouseCook(),
                guestHouseDetails.getGuestHouseWifi(),
                guestHouseDetails.getGuestHouseParking(),
                guestHouseDetails.getGuestHouseParty(),
        };
    }


    /** 프론트에서 들어 온 태그 배열을 정렬하여 DB에 저장되는 태그 문자열 형식으로 변환하여 주는 메소드 **/
    default String tagStrArrToTagStrForFilter(String[] tag) {

        StringBuilder likeStringBuilder = new StringBuilder("%");

        if(tag == null || tag.length == 0)
            return likeStringBuilder.toString();

        //태그 정렬
        Arrays.sort(tag);

        String[] formattedTagArr = Stream.of(tag).map(plainTagStr -> "|" + plainTagStr + "|").toArray(String[]::new);


        //DB에 저장되어 있는 문자열 형식으로 변환

        for (int i = 0; i < tag.length; i++) {
            likeStringBuilder.append(formattedTagArr[i] + "%");
        }

        return likeStringBuilder.toString();
    }





}
