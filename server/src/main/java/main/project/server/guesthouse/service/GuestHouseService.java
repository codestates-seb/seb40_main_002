package main.project.server.guesthouse.service;

import lombok.RequiredArgsConstructor;
import main.project.server.exception.BusinessException;
import main.project.server.exception.ExceptionCode;
import main.project.server.guesthouse.dto.QueryStringDto;
import main.project.server.guesthouse.dto.ReserveStatisticsDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.entity.enums.GuestHouseStatus;
import main.project.server.guesthouse.mapper.GuestHouseMapper;
import main.project.server.guesthouse.guesthouseimage.entity.GuestHouseImage;
import main.project.server.guesthouse.repository.GuestHouseRepository;
import main.project.server.guesthouse.guesthouseimage.repository.GuestHouseImageRepository;
import main.project.server.member.entity.Member;
import main.project.server.guesthouse.room.entity.Room;
import main.project.server.guesthouse.room.service.RoomService;
import main.project.server.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@RequiredArgsConstructor
@Service
public class GuestHouseService {

    @Value("${images.upload-path}")
    private String guestHouseImagePath;
    private final GuestHouseRepository repository;
    private final GuestHouseImageRepository guestHouseImageRepository;
    private final RoomService roomService;
    private final GuestHouseMapper guestHouseMapper;


    //-- 컨트롤러와 직접 연결된 메소드
    public GuestHouse createGuestHouse(GuestHouse guestHouse, MultipartFile[] guestHouseImages, List<Room> rooms, MultipartFile[] roomImages) throws IOException {

        // 게스트하우스 생성
        GuestHouse savedGuestHouse = repository.save(guestHouse);

        // 룸 생성
        roomService.createRoom(rooms, roomImages, savedGuestHouse.getGuestHouseId());

        // 게스트하우스의 이미지가 넘어 오지 않은 경우, 처리하지 않는다
        if(guestHouseImages != null && guestHouseImages.length != 0 && !guestHouseImages[0].getOriginalFilename().equals(""))
        {
            // 게스트 하우스 이미지 파일 저장
            List<String> imageUrls = saveFiles(guestHouseImages, savedGuestHouse.getGuestHouseId());

            // "게스트하우스 엔티티" 객체에 "게스트하우스 이미지 엔티티" 세팅
            guestHouse.setGuestHouseImage(urlListToGuestHouseImageList(guestHouse, imageUrls));
        }

        return guestHouse;
    }


    public GuestHouse modifyGuestHouse(GuestHouse guestHouse, MultipartFile[] guestHouseImages, String memberId,
                                       List<List<Room>> rooms, MultipartFile[] roomImages, MultipartFile[] newRoomImages) throws IOException {

        // 기존 게스트하우스 데이터 가져오기
        GuestHouse existsGuestHouse = verifyExistsGuestHouse(guestHouse.getGuestHouseId());

        // 수정할 수 있는 멤버가 맞는지 검증
        verifyOwnGuestHouse(existsGuestHouse, memberId);

        // "게스트 하우스 이미지 엔티티" 가 가진 URL만 리스트화
        List<String> urlList = existsGuestHouse.getGuestHouseImage().stream().map(
                guestHouseImage -> new String(guestHouseImage.getGuestHouseImageUrl())).collect(Collectors.toList());

        // 룸 정보 업데이트
        roomService.updateRoom(rooms, roomImages, newRoomImages, existsGuestHouse.getGuestHouseId());

        // 기존 이미지 데이터 삭제
        guestHouseImageRepository.deleteAllByGuestHouse(guestHouse);

        // 기존 이미지 파일 삭제
        deleteAllGuestHouseImageByGuestHouse(urlList,guestHouse.getGuestHouseId());

        // 게스트하우스의 이미지가 넘어 오지 않은 경우, 처리하지 않는다 (현재 구조는 put 이므로 클라이언트가 모든 데이터를 다시 다 보내는 방식)
        if(guestHouseImages != null && guestHouseImages.length != 0 && !guestHouseImages[0].getOriginalFilename().equals(""))
        {
            // 게스트 하우스 이미지 파일 저장
            List<String> imageUrls = saveFiles(guestHouseImages, guestHouse.getGuestHouseId());

            // "게스트하우스 엔티티" 객체에 "게스트하우스 이미지 엔티티" 세팅
            guestHouse.setGuestHouseImage(urlListToGuestHouseImageList(guestHouse, imageUrls));
        }

        // GuestHouseDetails가 새롭게 생성되지 않도록, DB에서 가져온 GuestHouseDetails의 id를 업데이트될 게스트하우스의 GuestHouseDetails에 세팅
        guestHouse.getGuestHouseDetails().setGuestHouseDetailsId(existsGuestHouse.getGuestHouseDetails().getGuestHouseDetailsId());

        // "게스트 하우스 엔티티"의 필드값들을 기존 값으로 세팅
        guestHouse.setGuestHouseReviewCount(existsGuestHouse.getGuestHouseReviewCount());
        guestHouse.setGuestHouseStar(existsGuestHouse.getGuestHouseStar());
        guestHouse.setGuestHouseStatus(existsGuestHouse.getGuestHouseStatus());

        // 기존 찜 갯수 저장
        guestHouse.setHearts(existsGuestHouse.getHearts());

        // entity 저장
        repository.save(guestHouse);

        return guestHouse;

    }

    /** ID값으로 게스트하우스를 가져옴과 동시에, 존재하는 게스트하우스인지 검증 **/
    public GuestHouse findGuestHouse(Long guestHouseId) {

        return verifyExistsGuestHouse(guestHouseId);
    }


    public void changeGuestHouseStatusAsClosed(Long guestHouseId, String memberId) {

        GuestHouse guestHouse = verifyExistsGuestHouse(guestHouseId);

        //삭제 처리를 할 수 있는 멤버가 맞는지 검증
        verifyOwnGuestHouse(guestHouse, memberId);

        guestHouse.setGuestHouseStatus(GuestHouseStatus.CLOSED);
    }

    public Page<GuestHouse> findGuestHouseByMember(String memberId, Integer page, Integer size) {

        return repository.findGuestHouseByMember(Member.Member(memberId), PageRequest.of(page-1, size));
    }


    public Page<GuestHouse> findGuestHouseByMainFilter(QueryStringDto.MainFilterDto queryStringDto) {

        String[] sepTagArr = guestHouseMapper.plainTagArrToSeperTagArr(queryStringDto.getTag());

        String sortValue = queryStringDto.getSort();

        //필터링으로 인한 게스트하우스 리스트 구하기
        Page<GuestHouse> guestHouseByFilter = repository.findGuestHouseByFilter(
                queryStringDto.getCityId(),
                sepTagArr,
                queryStringDto.getStart(),
                queryStringDto.getEnd(),
                PageRequest.of(queryStringDto.getPage() - 1, queryStringDto.getSize()),
                sortValue);

        return guestHouseByFilter;
    }



    //-- ~ 컨트롤러와 직접 연결된 메소드


    public GuestHouse verifyExistsGuestHouse(Long guestHouseId) {

        return repository.findById(guestHouseId).orElseThrow(()
                -> {return new BusinessException(ExceptionCode.NOT_EXISTS_GUESTHOUSE);});
    }


    private List<String> saveFiles(MultipartFile[] images, Long guestHouseId) throws IOException {

        String uploadPath = guestHouseImagePath + guestHouseId; //저장 디렉토리 경로

        List<String> imageUrl = new ArrayList<>();

        for (MultipartFile multipartFile : images) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            FileUtil.saveFile(uploadPath, fileName, multipartFile);

            String totalUrl = "/images/guesthouse/"+ guestHouseId + "/" + fileName;
            imageUrl.add(totalUrl);
        }

        return imageUrl;
    }

    private List<String> deleteAllGuestHouseImageByGuestHouse(List<String> imageUrl, Long guestHouseId) throws IOException {

        for (String url : imageUrl) {
            FileUtil.deleteFile(url);
        }

        return imageUrl;
    }

    private List<GuestHouseImage> urlListToGuestHouseImageList(GuestHouse guestHouse,List<String> urls) {

        return urls.stream().map(url ->
                GuestHouseImage.builder().guestHouse(guestHouse).guestHouseImageUrl(url).build()).collect(Collectors.toList());
    }

    /** 게스트 하우스의 소유 멤버와 처리 요청한 멤버가 동일한지 검증  **/
    public void verifyOwnGuestHouse(GuestHouse guestHouse, String memberId) {

        if (!guestHouse.getMember().getMemberId().equals(memberId)) {

            throw new BusinessException(ExceptionCode.NOT_OWN_GUESTHOUSE);
        }
    }

    public Page<GuestHouse> findAllGuestHouse(Integer page, Integer size, String[] tag, String sortValue) {

        String[] sepTagArr = guestHouseMapper.plainTagArrToSeperTagArr(tag);

        Page<GuestHouse> guestHousePage = repository.findAllGuestHouse(sepTagArr, PageRequest.of(page-1, size), sortValue);
        return guestHousePage;
    }

    public List<ReserveStatisticsDto> findAllReserveChartOfCreatedAt(String memberId,
                                                                     Long guestHouseId,
                                                                     String yearMonth) {

        //기존 게스트하우스 데이터 가져오기
        GuestHouse existsGuestHouse = verifyExistsGuestHouse(guestHouseId);

        //조회할 수 있는 멤버가 맞는지 검증
        verifyOwnGuestHouse(existsGuestHouse, memberId);

        String addedBarYearMonth = yearMonth+"-";
        List<Object[]> guestHouseReserveStatistics = repository.getGuestHouseReserveStatistics(
                guestHouseId, addedBarYearMonth);

        List<ReserveStatisticsDto> reserveStatisticsDtoList = guestHouseMapper
                .ObjectArrayToReserveStatisticsDtoList(guestHouseReserveStatistics);

        return reserveStatisticsDtoList;

    }
}
