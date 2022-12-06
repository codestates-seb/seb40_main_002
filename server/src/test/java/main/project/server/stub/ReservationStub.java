package main.project.server.stub;

import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.room.entity.Room;
import main.project.server.member.entity.Member;
import main.project.server.roomreservation.dto.RoomReservationDto;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import main.project.server.statistics.dto.AgeChartDto;
import main.project.server.statistics.dto.MonthlyReservationChartDto;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.util.List;

public class ReservationStub {

    public static RoomReservation getCompletedMemberReservation(LocalDate start, Member member, GuestHouse guestHouse) {

        return RoomReservation.builder()
                .roomReservationStart(start)
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .member(member)
                .guestHouse(guestHouse)
                .build();
    }

    public static RoomReservation getCompletedReservation(LocalDate start, GuestHouse guestHouse) {
        return RoomReservation.builder()
                .roomReservationStart(start)
                .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                .guestHouse(guestHouse)
                .build();
    }

    public static class MockReservation {

        public static RoomReservationDto.Post getRequestBody() {
            return RoomReservationDto.Post.builder()
                    .roomReservationStart(LocalDate.parse("2022-01-01"))
                    .roomReservationEnd(LocalDate.parse("2022-01-02"))
                    .build();
        }

        public static RoomReservation getRoomReservation() {
            return RoomReservation.builder()
                    .guestHouse(GuestHouse.GuestHouse(1L))
                    .room(Room.Room(1L))
                    .member(Member.Member("1"))
                    .roomReservationId(1L)
                    .roomReservationStart(LocalDate.parse("2022-01-01"))
                    .roomReservationEnd(LocalDate.parse("2022-01-02"))
                    .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                    .build();
        }

        public static RoomReservationDto.Response getResponseBody() {
            return RoomReservationDto.Response.builder()
                    .guestHouseId(1L)
                    .roomReservationId(1L)
                    .guestHouseName("숙소")
                    .roomName("방")
                    .roomImageUrl("URL")
                    .roomReservationStart(LocalDate.parse("2022-01-01"))
                    .roomReservationEnd(LocalDate.parse("2022-01-02"))
                    .roomReservationStatus(RoomReservationStatus.RESERVATION_COMPLETE)
                    .build();


        }

    }


    public static class MockReservationChart {
        public static MonthlyReservationChartDto getMonthlyChartDto() {
            return MonthlyReservationChartDto.builder()
                    .monthlyReservationList(List
                            .of(new MonthlyReservationChartDto.MonthlyReservation(1, 1L),
                                    new MonthlyReservationChartDto.MonthlyReservation(1, 1L)
                            ))
                    .totalCount(2L)
                    .build();
        }

        public static AgeChartDto getAgeChartDto() {
            return AgeChartDto.builder()
                    .ageGroupReservationList(List
                            .of(new AgeChartDto.AgeGroupReservation("20세 초과 30세 이하", 1L),
                                    new AgeChartDto.AgeGroupReservation("30세 초과 40세 이하", 1L)))
                    .totalCount(2L)
                    .build();
        }
    }


}