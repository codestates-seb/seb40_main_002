package main.project.server.roomreservation.service;

import lombok.RequiredArgsConstructor;
import main.project.server.exception.BusinessException;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.service.GuestHouseService;
import main.project.server.member.entity.Member;
import main.project.server.member.service.MemberService;
import main.project.server.room.entity.Room;
import main.project.server.room.entity.enums.RoomStatus;
import main.project.server.room.service.RoomService;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import main.project.server.roomreservation.repository.RoomReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomReservationService {

    // 맞는 guestHouseId, roomId 들어왔는지, 맞는 멤버인지 확인

    private final RoomReservationRepository roomReservationRepository;

    private final RoomService roomService;

    private final MemberService memberService;

    public RoomReservation createRoomReservation(RoomReservation roomReservation,
                                                 long guestHouseId,
                                                 long roomId, Principal principal) {

        if (roomService.findVerifiedRoom(roomId).getGuestHouse().getGuestHouseId()
                != guestHouseId) {
            throw new RuntimeException("createReservation Exception");
        }

        roomReservation.setRoomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE);
        roomReservation.addGuestHouse(GuestHouse.GuestHouse(guestHouseId));
        roomReservation.addRoom(Room.Room(roomId));
        roomReservation.addMember(Member.Member("테스터")); // 테스트용


        roomReservation.setRoomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE);
        return roomReservationRepository.save(roomReservation);
    }

    public void deleteRoomReservation(long guestHouseId, long roomId, long reservationId, Principal principal) {
        if (roomService.findVerifiedRoom(roomId).getGuestHouse().getGuestHouseId() != guestHouseId
                || findVerifiedRoomReservation(reservationId).getRoom().getRoomId() != roomId) {
            throw new RuntimeException("createReservation Exception");
        }

        // 멤버 검사
        RoomReservation findRoomReservation = findVerifiedRoomReservation(reservationId);
        findRoomReservation.setRoomReservationStatus(RoomReservationStatus.RESERVATION_CANCEL);

        roomReservationRepository.save(findRoomReservation);
    }

    public Page<RoomReservation> findMyReservation(Principal principal, int page, int size) {
        Member tempMember = memberService.findVerifiedMember("테스터"); // principal에서 추출할 것

        return roomReservationRepository.findByMember(tempMember, PageRequest.of(page, size,
                Sort.by("roomReservationId").descending()));
    }

    public RoomReservation findVerifiedRoomReservation(Long roomReservationId) {
        Optional<RoomReservation> optionalRoomReservation = roomReservationRepository.findById(roomReservationId);
        RoomReservation findRoomReservation =
                optionalRoomReservation.orElseThrow(() ->
                        new RuntimeException("cannot find Reservation"));
        return findRoomReservation;
    }
}
