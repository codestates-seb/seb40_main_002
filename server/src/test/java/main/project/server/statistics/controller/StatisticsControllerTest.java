package main.project.server.statistics.controller;

import com.google.gson.Gson;
import main.project.server.helper.StatisticsControllerTestHelper;
import main.project.server.statistics.dto.AgeChartDto;
import main.project.server.statistics.dto.MonthlyReservationChartDto;
import main.project.server.statistics.service.StatisticsService;
import main.project.server.stub.ReservationStub;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTest implements StatisticsControllerTestHelper {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private StatisticsService statisticsService;

    @Test
    void monthlyChartTest() throws Exception {

        // given
        Long guestHouseId = 1L;
        String year = "2022";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("year", year);

        MonthlyReservationChartDto chart = ReservationStub.MockReservationChart.getMonthlyChartDto();

        given(statisticsService.getMonthlyReservationChart(Mockito.anyLong(), Mockito.anyInt(), Mockito.any()))
                .willReturn(chart);

        // when
        ResultActions actions = mockMvc.perform(getRequestBuilder(getMonthlyChartUri(), guestHouseId, queryParams));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalCount").value(chart.getTotalCount()))
                .andExpect(jsonPath("$.data.monthlyReservationList.size()").value(chart.getMonthlyReservationList().size()));
    }

    @Test
    void ageChartTest() throws Exception {

        // given
        Long guestHouseId = 1L;
        String year = "2022";
        String month = "1";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("year", year);
        queryParams.add("month", month);

        AgeChartDto chart = ReservationStub.MockReservationChart.getAgeChartDto();

        given(statisticsService.getAgeChart(Mockito.anyLong(), Mockito.any(), Mockito.any()))
                .willReturn(chart);

        // when
        ResultActions actions = mockMvc.perform(getRequestBuilder(getAgeChartUri(), guestHouseId, queryParams));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalCount").value(chart.getTotalCount()))
                .andExpect(jsonPath("$.data.ageGroupReservationList.size()").value(chart.getAgeGroupReservationList().size()));
    }

}