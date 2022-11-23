package main.project.server.guesthouse.service;

import lombok.RequiredArgsConstructor;
import main.project.server.exception.BusinessException;
import main.project.server.exception.ExceptionCode;
import main.project.server.guesthouse.dto.QueryStringDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.entity.enums.GuestHouseStatus;
import main.project.server.guesthousedetails.repository.GuestHouseDetailsRepository;
import main.project.server.guesthouseimage.entity.GuestHouseImage;
import main.project.server.guesthouse.repository.GuestHouseRepository;
import main.project.server.guesthouseimage.repository.GuestHouseImageRepository;
import main.project.server.member.entity.Member;
import main.project.server.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class GuestHouseService {

    @Value("${images.upload-path}")
    private String guestHouseImagePath;

    private final GuestHouseRepository repository;

    private final GuestHouseDetailsRepository guestHouseDetailsRepository;

    private final GuestHouseImageRepository guestHouseImageRepository;

    //-- 컨트롤러와 직접 연결된 메소드
    public GuestHouse createGuestHouse(GuestHouse guestHouse, MultipartFile[] guestHouseImages) throws IOException {

        //entity 저장
        GuestHouse savedGuestHouse = repository.save(guestHouse);

        //파일이 없을 경우에 대한 더 적절한 처리가 필요
        if(guestHouseImages != null && guestHouseImages.length != 0 && !guestHouseImages[0].getOriginalFilename().equals(""))
        {
            //이미지 저장
            List<String> imageUrls = saveFiles(guestHouseImages, savedGuestHouse.getGuestHouseId());
            guestHouse.setGuestHouseImage(urlListToGuestHouseImageList(guestHouse, imageUrls));
        }

        return guestHouse;
    }


    public GuestHouse modifyGuestHouse(GuestHouse guestHouse, MultipartFile[] guestHouseImages, String memberId) throws IOException {


        //기존 게스트하우스 데이터 가져오기
        GuestHouse existsGuestHouse = verifyExistsGuestHouse(guestHouse.getGuestHouseId());

        //수정할 수 있는 멤버가 맞는지 검증
        verifyOwnGuestHouse(existsGuestHouse, memberId);

        //url만 String으로 매핑
        List<String> urlList = existsGuestHouse.getGuestHouseImage().stream().map(
                guestHouseImage -> new String(guestHouseImage.getGuestHouseImageUrl())).collect(Collectors.toList());


        //기존 이미지 파일 삭제, 기존 이미지 데이터 삭제
        guestHouseImageRepository.deleteAllByGuestHouse(guestHouse);
        deleteAllGuestHouseImageByGuestHouse(urlList,guestHouse.getGuestHouseId());


        //파일이 없을 경우에 대한 더 적절한 처리가 필요
        if(guestHouseImages != null && guestHouseImages.length != 0 && !guestHouseImages[0].getOriginalFilename().equals(""))
        {
            //이미지 저장
            List<String> imageUrls = saveFiles(guestHouseImages, guestHouse.getGuestHouseId());
            guestHouse.setGuestHouseImage(urlListToGuestHouseImageList(guestHouse, imageUrls));
        }

        //GuestHouseDetails가 새롭게 생성되지 않도록, DB에서 가져온 GuestHouseDetails의 id를 세팅
        guestHouse.getGuestHouseDetails().setGuestHouseDetailsId(existsGuestHouse.getGuestHouseDetails().getGuestHouseDetailsId());

        //기존 값으로 세팅
        guestHouse.setGuestHouseReviewCount(existsGuestHouse.getGuestHouseReviewCount());
        guestHouse.setGuestHouseStar(existsGuestHouse.getGuestHouseStar());
        guestHouse.setGuestHouseStatus(existsGuestHouse.getGuestHouseStatus());

        //기존 찜 갯수 저장
        guestHouse.setHearts(existsGuestHouse.getHearts());

        //entity 저장
        repository.save(guestHouse);

        return guestHouse;

    }

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

        //태그 정렬
        Arrays.sort(queryStringDto.getTag());

        //DB에 저장되어 있는 문자열 형식으로 변환
        StringBuilder likeStringBuilder = new StringBuilder("%");
        String[] tag = queryStringDto.getTag();
        for (int i = 0; i < tag.length; i++) {
            likeStringBuilder.append(tag[i] + "%");
        }


        //오더바이 정렬 구하기
        String sortValue = queryStringDto.getSort();

        Sort sort = Sort.by(Sort.Direction.DESC, "guest_house_id"); //기본, 등록순 내림차순

        if (sortValue.equals("star")) {

            sort = Sort.by(Sort.Direction.DESC,"guest_house_star");

        } else if (sortValue.equals("review")) {

           sort = Sort.by(Sort.Direction.DESC,"guest_house_review_count");
        }


        //필터링으로 인한 게스트하우스 리스트 구하기
        Page<GuestHouse> guestHouseByFilter = repository.findGuestHouseByFilter(
                queryStringDto.getCityId(),
                likeStringBuilder.toString(),
                queryStringDto.getStart(),
                queryStringDto.getEnd(),
                PageRequest.of(queryStringDto.getPage() - 1, queryStringDto.getSize(), sort));

        return guestHouseByFilter;
    }



    //-- ~ 컨트롤러와 직접 연결된 메소드


    public GuestHouse verifyExistsGuestHouse(Long guestHouseId) {

        return repository.findById(guestHouseId).orElseThrow(()
                -> {return new BusinessException(ExceptionCode.NOT_EXISTS_GUESTHOUSE);});
    }


    private List<String> saveFiles(MultipartFile[] images, Long guestHouseId) throws IOException {

        String uploadPath = guestHouseImagePath + guestHouseId; //저장 디렉토리 경로

//        Long currentTimeMillis = System.currentTimeMillis();//현재 시간 밀리세컨드로

        List<String> imageUrl = new ArrayList<>();

        for (MultipartFile multipartFile : images) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            FileUtil.saveFile(uploadPath, fileName, multipartFile);

            String totalUrl = guestHouseImagePath+ "/" + fileName;
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
    private void verifyOwnGuestHouse(GuestHouse guestHouse, String memberId) {

        if (!guestHouse.getMember().getMemberId().equals(memberId)) {

            throw new BusinessException(ExceptionCode.NOT_OWN_GUESTHOUSE);
        }
    }

    public Page<GuestHouse> findAllGuestHouse(Integer page, Integer size, String sortValue) {

        //오더바이 정렬 구하기
        Sort sort;
        if (sortValue.equals("star")) sort = Sort.by(Sort.Direction.DESC,"guestHouseStar");
        else if(sortValue.equals("review")) sort = Sort.by(Sort.Direction.DESC,"guestHouseReviewCount");
        else sort = Sort.by(Sort.Direction.DESC, "guestHouseId"); //기본, 등록순 내림차순

        Page<GuestHouse> guestHousePage = repository.findAll(PageRequest.of(page-1, size, sort));
        return guestHousePage;
    }
}
