package main.project.server.guesthouse.all;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class AllGuestHouseTests {



    @DisplayName("모든 게스트하우스 페이지네이션 - 모든 파라미터 X")
    @Test
    void allParametersNoting() {

        


    }


}
