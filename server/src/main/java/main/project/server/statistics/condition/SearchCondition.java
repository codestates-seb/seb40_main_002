package main.project.server.statistics.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * 검색 조건을 정의한 클래스
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class SearchCondition {

    private Integer year;

    private Integer month;

    private Long guestHouseId;
}
