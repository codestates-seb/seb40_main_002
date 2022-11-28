package main.project.server.chart.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import main.project.server.chart.condition.SearchCondition;
import main.project.server.chart.dto.AgeChartDto;
import main.project.server.chart.dto.MonthlyReservationChartDto;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;

import java.util.List;

import static main.project.server.member.entity.QMember.member;
import static main.project.server.roomreservation.entity.QRoomReservation.roomReservation;

@RequiredArgsConstructor
public class CustomizedRoomReservationRepositoryImpl implements CustomizedRoomReservationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /** 특정 게스트하우스의 해당연도 월별 예약 건수 통계 쿼리 메서드 */
    @Override
    public List<MonthlyReservationChartDto.MonthlyReservation> findByYearGroupByMonth(Long guestHouseId, int year) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                MonthlyReservationChartDto.MonthlyReservation.class,
                                roomReservation.roomReservationStart.month(),
                                roomReservation.count()))
                .from(roomReservation)
                .where(roomReservation.guestHouse.guestHouseId.eq(guestHouseId)
                        .and(roomReservation.roomReservationStatus.eq(RoomReservationStatus.RESERVATION_COMPLETE))
                        .and(roomReservation.roomReservationStart.year().eq(year)))
                .groupBy(roomReservation.roomReservationStart.month())
                .fetch();
    }

    /**
     * 특정 게스트하우스의 연령별 예약건수 통계 쿼리 메서드
     * @param condition 검색 조건 동적 선택 - year, month
     */
    @Override
    public List<AgeChartDto.AgeGroupReservation> findGroupByAge(Long guestHouseId, SearchCondition condition) {
        // 멤버 출생년도를 통해 예약일 당시 나이 계산
        NumberExpression<Integer> year = roomReservation.roomReservationStart.year();
        NumberExpression<Integer> integerNumberExpression = member.memberBirth.substring(0, 4).castToNum(Integer.class);
        NumberExpression<Integer> subtract = year.subtract(integerNumberExpression);

        // 연령별 분류
        Expression<String> cases = new CaseBuilder()
                .when(subtract.between(0, 20)).then("20세 이하")
                .when(subtract.between(20, 30)).then("20세 초과 30세 이하")
                .when(subtract.between(30, 40)).then("30세 초과 40세 이하")
                .when(subtract.between(40, 50)).then("40세 초과 50세 이하")
                .otherwise("50세 이상");

        return jpaQueryFactory.select(
                        Projections.constructor(
                                AgeChartDto.AgeGroupReservation.class,
                                cases,
                                roomReservation.count()))
                .from(roomReservation)
                .join(roomReservation.member, member) // inner join
                .where(roomReservation.guestHouse.guestHouseId.eq(guestHouseId)
                        .and(roomReservation.roomReservationStatus.eq(RoomReservationStatus.RESERVATION_COMPLETE))
                        .and(yearEq(condition.getYear()))
                        .and(monthEq(condition.getMonth())))
                .groupBy(cases)
                .fetch();
    }


    private BooleanExpression yearEq(Integer year) {
        return year != null ? roomReservation.roomReservationStart.year().eq(year) : null;
    }

    private BooleanExpression monthEq(Integer month) {
        return month != null ? roomReservation.roomReservationStart.month().eq(month) : null;
    }
}
