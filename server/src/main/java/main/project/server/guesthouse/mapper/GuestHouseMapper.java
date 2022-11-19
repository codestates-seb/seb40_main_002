package main.project.server.guesthouse.mapper;


import main.project.server.city.City;
import main.project.server.guesthouse.dto.GuestHouseDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.entity.enums.GuestHouseStatus;
import main.project.server.guesthousedetails.mapper.GuestHouseDetailsMapper;
import main.project.server.guesthouseroom.mapper.GuestHouseRoomMapper;
import main.project.server.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GuestHouseMapper {

    default public GuestHouse guestHouseDtoPostToGuestHouse(GuestHouseDto.Post dto, String memberId){

        return GuestHouse.builder()
                .guestHouseName(dto.getGuestHouseName())
                .member(Member.Member(memberId))
                .city(City.City(dto.getCityId()))
                .guestHouseLocation(dto.getGuestHouseLocation())
                .guestHouseAddress(dto.getGuestHouseAddress())
                .guestHousePhone(dto.getGuestHousePhone())
                .guestHouseStatus(GuestHouseStatus.OPEN)
                .guestHouseTag(createSortedTagString(dto.getGuestHouseTag()))
                .guestHouseInfo(dto.getGuestHouseInfo())
                .build();

    }


    default public GuestHouse guestHouseDtoPutToGuestHouse(GuestHouseDto.Put dto, String memberId){

        return GuestHouse.builder()
                .guestHouseName(dto.getGuestHouseName())
                .member(Member.Member(memberId))
                .city(City.City(dto.getCityId()))
                .guestHouseLocation(dto.getGuestHouseLocation())
                .guestHouseAddress(dto.getGuestHouseAddress())
                .guestHousePhone(dto.getGuestHousePhone())
                .guestHouseStatus(GuestHouseStatus.OPEN)
                .guestHouseTag(createSortedTagString(dto.getGuestHouseTag()))
                .guestHouseInfo(dto.getGuestHouseInfo())
                .build();
    }

    default List<GuestHouseDto.response> guestHouseListToGuestHouseResponse(
            List<GuestHouse> guestHouseList,
            GuestHouseDetailsMapper guestHouseDetailsMapper) {

        return guestHouseList.stream().map(guestHouse -> {

            return GuestHouseDto.response.builder()
                    .guestHouseId(guestHouse.getGuestHouseId())
                    .guestHouseName(guestHouse.getGuestHouseName())
                    .memberId(guestHouse.getMember().getMemberId())
                    .memberNickname(guestHouse.getMember().getMemberNickname())
                    .memberPhone(guestHouse.getMember().getMemberPhone())
                    .memberImageUrl(guestHouse.getMember().getMemberImageUrl())
                    .guestHouseLocation(guestHouse.getGuestHouseLocation())
                    .guestHouseAddress(guestHouse.getGuestHouseAddress())
                    .guestHousePhone(guestHouse.getGuestHousePhone())
                    .guestHouseStatus(guestHouse.getGuestHouseStatus())
                    .guestHouseDetails(guestHouseDetailsMapper.guestHouseDetailsToGuestHouseDetailsDtoResponse(guestHouse.getGuestHouseDetails()))
                    .guestHouseStar(guestHouse.getGuestHouseStar())
                    .guestHouseTag(createSortedTagArray(guestHouse.getGuestHouseTag()))
                    .guestHouseImage(guestHouse.guestHouseImageListToUrlList())
                    .guestHouseInfo(guestHouse.getGuestHouseInfo())
                    .guestHouseRoom(null)
                    .createdAt(guestHouse.getCreatedAt().toString())
                    .modifiedAt(guestHouse.getModifiedAt().toString())
                    .build();

        }).collect(Collectors.toList());
    }




    default String createSortedTagString(String[] tags) {

        Arrays.sort(tags);

        StringBuilder stringBuilder = new StringBuilder();
        for (String tag : tags) {

            stringBuilder.append("|" + tag + "|");
        }
        return stringBuilder.toString();
    }

    default String[] createSortedTagArray(String tags) {

        String[] splStr;

        if(tags == null) //태그가 없는 경우
            return new String[0];

        if (tags.contains("||")) { //2개 이상 경우

            splStr = tags.split("\\|\\|");
            // |dd, ddd|, ...
        }
        else { //1개 경우

            splStr = new String[]{tags};
            // |dd|
        }

        String[] tagArray = Arrays.stream(splStr).map(str ->
                new String(str.replace("|", ""))).toArray(str -> new String[str]);

        return tagArray;
    }


    default GuestHouseDto.response guestHouseToSingleGuestHouseResponse(GuestHouse guestHouse, GuestHouseDetailsMapper guestHouseDetailsMapper) {

        Member adminMember = guestHouse.getMember();

        return GuestHouseDto.response.builder()
                .guestHouseId(guestHouse.getGuestHouseId())
                .guestHouseName(guestHouse.getGuestHouseName())
                .memberId(adminMember.getMemberId())
                .memberNickname(adminMember.getMemberNickname())
                .memberPhone(adminMember.getMemberPhone())
                .memberImageUrl(adminMember.getMemberImageUrl())
                .guestHouseLocation(guestHouse.getGuestHouseLocation())
                .guestHouseAddress(guestHouse.getGuestHouseAddress())
                .guestHousePhone(guestHouse.getGuestHousePhone())
                .guestHouseStatus(guestHouse.getGuestHouseStatus())
                .guestHouseDetails(guestHouseDetailsMapper.guestHouseDetailsToGuestHouseDetailsDtoResponse(guestHouse.getGuestHouseDetails()))
                .guestHouseStar(guestHouse.getGuestHouseStar())
                .guestHouseTag(createSortedTagArray(guestHouse.getGuestHouseTag()))
                .guestHouseImage(guestHouse.guestHouseImageListToUrlList()) //리스트, 처리 필요
                .guestHouseInfo(guestHouse.getGuestHouseInfo())
                .guestHouseRoom(null) //리스트, 처리 필요
                .createdAt(guestHouse.getCreatedAt().toString())
                .modifiedAt(guestHouse.getModifiedAt().toString())
                .build();
    }


}
