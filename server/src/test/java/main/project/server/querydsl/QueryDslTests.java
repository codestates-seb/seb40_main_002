package main.project.server.querydsl;


import main.project.server.chart.dto.AlotReserveGuestHouseDto;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.repository.CustomizedGuestHouseRepositoryImpl;
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
    CustomizedGuestHouseRepositoryImpl customizedGuestHouseRepository;

    @Test
    void tests(){
        List<AlotReserveGuestHouseDto> chartOfGuestHouseForReserveCountInPeriod = customizedGuestHouseRepository.getChartOfGuestHouseForReserveCountInPeriod(new Short[]{1, 0, 0});
        System.out.println();
    }
}
