package main.project.server.helper;

public interface StatisticsControllerTestHelper extends ControllerTestHelper {

    String MONTHLY_CHART_URL = "/api/auth/monthly-chart";
    String AGE_CHART_URL = "/api/auth/age-chart";
    String RESOURCE_URI = "/{guest-house-id}";

    default String getMonthlyChartUri() {
        return MONTHLY_CHART_URL + RESOURCE_URI;
    }

    default String getAgeChartUri() {
        return AGE_CHART_URL + RESOURCE_URI;
    }
}
