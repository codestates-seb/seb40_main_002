package main.project.server.statistics.condition;

import lombok.Getter;
import lombok.Setter;


/**
 * 검색 조건을 정의한 클래스
 */
@Getter
@Setter
public class SearchCondition {

    private Integer year;

    private Integer month;

    private Long guestHouseId;
}
