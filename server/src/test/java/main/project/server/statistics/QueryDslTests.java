package main.project.server.statistics;


import main.project.server.config.DatabaseCleanup;
import main.project.server.statistics.dto.ReserveCountOfGuestHouseDto;
import main.project.server.statistics.repository.StatisticsRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

@ActiveProfiles("dev")
@SpringBootTest
@Transactional
public class QueryDslTests {

    @Autowired
    StatisticsRepositoryImpl customizedGuestHouseRepository;

    @Autowired
    DatabaseCleanup databaseCleanup;


    @BeforeEach
    void beforeAll() {

    }


    @Test
    void tests(){


        List<ReserveCountOfGuestHouseDto> chartOfGuestHouseForReserveCountInPeriod = customizedGuestHouseRepository.getChartOfGuestHouseForReserveCountInPeriod(new Integer[]{1, 0, 0},10);
        System.out.println();
    }
}
