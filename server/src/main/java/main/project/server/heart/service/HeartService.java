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

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final GuestHouseRepository guestHouseRepository;
    private final MemberService memberService;
    private final GuestHouseService guestHouseService;


    public String clickHeart(String memberId, Long guestHouseId){
        try {
            Heart heart = heartRepository.findByMemberMemberIdAndGuestHouseGuestHouseId(memberId, guestHouseId).orElseThrow();
            GuestHouse guestHouse = guestHouseService.verifyExistsGuestHouse(guestHouseId);
            Member member = memberService.findVerifiedMember(memberId);

            Boolean preHeart = heart.getHeartStatus();

            if (preHeart) {
                heart.setHeartStatus(false);
                heartRepository.saveAndFlush(heart);

                member.getHeart().add(heart);
                memberRepository.saveAndFlush(member);

                guestHouse.setHearts(guestHouse.getHearts() - 1);
                guestHouseRepository.saveAndFlush(guestHouse);

                return "Heart Off";
            } else {
                heart.setHeartStatus(true);
                heartRepository.saveAndFlush(heart);

                member.getHeart().add(heart);
                memberRepository.saveAndFlush(member);

                guestHouse.setHearts(guestHouse.getHearts() + 1);
                guestHouseRepository.saveAndFlush(guestHouse);

                return "Heart On";
            }
        } catch (Exception e) {     // preHeart 없는 경우 -> 생성 및 하트 적용
            Heart heart = new Heart();
            Member member = memberService.findVerifiedMember(memberId);
            GuestHouse guestHouse = guestHouseService.verifyExistsGuestHouse(guestHouseId);

            heart.setMember(member);
            heart.setGuestHouse(guestHouse);
            heart.setHeartStatus(true);
            heartRepository.saveAndFlush(heart);

            guestHouse.setHearts(guestHouse.getHearts() + 1);
            guestHouseRepository.saveAndFlush(guestHouse);

            return "Heart On";
        }
    }

    public Boolean heartStatus(String memberId, Long guestHouseId) {
        Heart heart = heartRepository.findByMemberMemberIdAndGuestHouseGuestHouseId(memberId, guestHouseId)
                .orElseThrow(() -> new NoSuchElementException("No value present"));

        return heart.getHeartStatus();
    }

    // 멤버 찜 조회(페이지)
    public Page<Heart> getHeartPageByMember(int page, int size, String memberId){

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("heartId").descending());

        return heartRepository.findByMemberMemberId(memberId, pageable);
    }
}


