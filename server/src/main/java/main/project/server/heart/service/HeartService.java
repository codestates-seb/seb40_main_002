package main.project.server.heart.service;

import lombok.RequiredArgsConstructor;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.repository.GuestHouseRepository;
import main.project.server.guesthouse.service.GuestHouseService;
import main.project.server.heart.entity.Heart;
import main.project.server.heart.repository.HeartRepository;
import main.project.server.member.entity.Member;
import main.project.server.member.repository.MemberRepository;
import main.project.server.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final GuestHouseRepository guestHouseRepository;
    private final MemberService memberService;
    private final GuestHouseService guestHouseService;

    // 찜하기 누를때
    public String clickHeart(String memberId, Long guestHouseId){
        try {
            // 게하와 사용자 기준 찜하기 기록을 조회
            Heart heart = heartRepository.findByMemberMemberIdAndGuestHouseGuestHouseId(memberId, guestHouseId).orElseThrow();

            // 게스트하우스 조회
            GuestHouse guestHouse = guestHouseService.verifyExistsGuestHouse(guestHouseId);

            // 사용자 조회
            Member member = memberService.findVerifiedMember(memberId);

            // 이전 찜하기 상태(찜한
            Boolean preHeart = heart.getHeartStatus();

            // 이전 찜하기 상태가 true -> 찜한 상태라면
            if (preHeart) {
                heart.setHeartStatus(false);    // 상태를 false -> 찜하지 않은 상태로(찜하기취소)
                heartRepository.saveAndFlush(heart);    // 찜하기 저장

                member.getHeart().add(heart);   // 사용자 찜목록에 상태가 변한 찜하기 상태 저장
                memberRepository.saveAndFlush(member);  // 사용자 저장

                guestHouse.setHearts(guestHouse.getHearts() - 1);   // 게하 찜 개수 감소
                guestHouseRepository.saveAndFlush(guestHouse);  // 게하 저장

                return "Heart Off";
            } else {    // 이전 찜하기가 false -> 찜하지 않은 상태
                heart.setHeartStatus(true);     // 상태를 true -> 찜한 상태로(찜하기)
                heartRepository.saveAndFlush(heart);    // 찜하기 저장

                member.getHeart().add(heart);   // 사용자 찜목록에 상태가 변한 찜하기 상태 저장
                memberRepository.saveAndFlush(member);  // 사용자 저장

                guestHouse.setHearts(guestHouse.getHearts() + 1);   // 게하 찜 개수 증가
                guestHouseRepository.saveAndFlush(guestHouse);  // 게하 저장

                return "Heart On";
            }
        } catch (Exception e) {     // preHeart 없는 경우, 이전 찜 기록이 없는 경우 -> 생성 및 하트 적용
            Heart heart = new Heart();      // 찜하기 만듬
            Member member = memberService.findVerifiedMember(memberId);     // 사용자 조회
            GuestHouse guestHouse = guestHouseService.verifyExistsGuestHouse(guestHouseId);     // 게하 조회

            heart.setMember(member);    // 사용자 입력
            heart.setGuestHouse(guestHouse);    // 게하 입력
            heart.setHeartStatus(true);     // 상태 ture(찜) 입력
            heartRepository.saveAndFlush(heart);    // 저장

            guestHouse.setHearts(guestHouse.getHearts() + 1);   // 게하 찜 개수 증가
            guestHouseRepository.saveAndFlush(guestHouse);  // 게하 저장

            return "Heart On";
        }
    }

    // 찜 상태 조회
    public Boolean heartStatus(String memberId, Long guestHouseId) {

        Heart heart = heartRepository.findByMemberMemberIdAndGuestHouseGuestHouseId(memberId, guestHouseId).orElse(null);

        if(heart == null) return false;

        else return heart.getHeartStatus();
    }

    // 멤버 찜 조회(페이지)
    public Page<Heart> getHeartPageByMember(int page, int size, String memberId){

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("heartId").descending());

        return heartRepository.findByMemberMemberIdAndHeartStatusIs(memberId, true, pageable);
    }
}


