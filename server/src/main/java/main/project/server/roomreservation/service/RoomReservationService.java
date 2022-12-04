package main.project.server.roomreservation.service;

import lombok.RequiredArgsConstructor;
import main.project.server.exception.BusinessException;
import main.project.server.exception.ExceptionCode;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.member.entity.Member;
import main.project.server.member.service.MemberService;
import main.project.server.guesthouse.room.entity.Room;
import main.project.server.guesthouse.room.entity.enums.RoomStatus;
import main.project.server.guesthouse.room.repository.RoomRepository;
import main.project.server.guesthouse.room.service.RoomService;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import main.project.server.roomreservation.repository.RoomReservationRepository;
import main.project.server.statistics.condition.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Objects;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomReservationService {

    // 맞는 guestHouseId, roomId 들어왔는지, 맞는 멤버인지 확인

    private final RoomReservationRepository roomReservationRepository;

    private final RoomService roomService;

    private final MemberService memberService;

    private final RoomRepository roomRepository;

    public RoomReservation createRoomReservation(RoomReservation roomReservation,
                                                 Long guestHouseId,
                                                 Long roomId, Principal principal) {

        if (!Objects.equals(roomService.findVerifiedRoom(roomId).getGuestHouse().getGuestHouseId(), guestHouseId)) {
            throw new BusinessException(ExceptionCode.NOT_MATCH_ROOM);
        }
        verifyAvailableReservation(guestHouseId, roomId, roomReservation); //예약 검증
        roomReservation.setRoomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE);
        roomReservation.addGuestHouse(GuestHouse.GuestHouse(guestHouseId));
        roomReservation.addRoom(Room.Room(roomId));
        roomReservation.addMember(Member.Member(principal.getName()));

        roomReservation.setRoomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE);
        return roomReservationRepository.save(roomReservation);
    }

    private void verifyAvailableReservation(Long guestHouseId, Long roomId, RoomReservation roomReservation) {

        String start = roomReservation.getRoomReservationStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String end = roomReservation.getRoomReservationEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Room> availableReserveRoomList = roomRepository.findAllAvailableReservation(guestHouseId, start, end, RoomStatus.ROOM_ENABLE.toString());

        boolean find = availableReserveRoomList.stream().anyMatch(room -> {
            if (room.getRoomId().equals(roomId)) return true;
            return false;
        });

        // 날짜 유효성 검증
        boolean notValidDay = (roomReservation.getRoomReservationStart().isAfter(roomReservation.getRoomReservationEnd())
            || roomReservation.getRoomReservationStart().isBefore(LocalDate.now()));

        if(!find || notValidDay)
            throw new BusinessException(ExceptionCode.NOT_AVAILABLE_RESERVATION);
    }


    public void deleteRoomReservation(Long guestHouseId, Long roomId, Long reservationId, Principal principal) {
        if (!Objects.equals(roomService.findVerifiedRoom(roomId).getGuestHouse().getGuestHouseId(), guestHouseId)) {
            throw new BusinessException(ExceptionCode.NOT_MATCH_ROOM);
        }
        if (!Objects.equals(findVerifiedRoomReservation(reservationId).getRoom().getRoomId(), roomId)) {
            throw new BusinessException(ExceptionCode.NOT_MATCH_RESERVATION);
        }

        // 멤버 검사
        RoomReservation findRoomReservation = findVerifiedRoomReservation(reservationId);
        findRoomReservation.setRoomReservationStatus(RoomReservationStatus.RESERVATION_CANCEL);

        roomReservationRepository.save(findRoomReservation);
    }

    public Page<RoomReservation> findMyReservation(Principal principal, SearchCondition condition, Integer page, Integer size) {
        Member tempMember = memberService.findVerifiedMember(principal.getName());

        List<RoomReservation> reservationList = roomReservationRepository.findMyReservationByGuestHouse(tempMember, condition);

        PageRequest pageRequest = PageRequest.of(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), reservationList.size());
        Page<RoomReservation> reservationPage = new PageImpl<>(reservationList.subList(start, end), pageRequest, reservationList.size());
        return reservationPage;
    }

    public RoomReservation findVerifiedRoomReservation(Long roomReservationId) {
        Optional<RoomReservation> optionalRoomReservation = roomReservationRepository.findById(roomReservationId);
        RoomReservation findRoomReservation =
                optionalRoomReservation.orElseThrow(() ->
                        new BusinessException(ExceptionCode.RESERVATION_NOT_FOUND));
        return findRoomReservation;
    }
}
