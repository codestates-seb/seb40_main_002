package main.project.server.stub;

import main.project.server.roomreservation.dto.RoomReservationDto;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import main.project.server.statistics.dto.MonthlyReservationChartDto;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.util.List;

public class ReservationStub {

    public static class MockReservation {
        public static RoomReservationDto.Post getRequestBody(HttpMethod method) {
            return RoomReservationDto.Post.builder()
                    .roomReservationStart(LocalDate.parse("2022-01-01"))
                    .roomReservationEnd(LocalDate.parse("2022-01-02"))
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
                                    new MonthlyReservationChartDto.MonthlyReservation(2, 2L)
                            ))
                    .totalCount(3L)
                    .build();
        }
    }
}