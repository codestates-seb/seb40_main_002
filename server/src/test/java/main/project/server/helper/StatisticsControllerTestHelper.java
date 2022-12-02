package main.project.server.helper;

public interface StatisticsControllerTestHelper extends ControllerTestHelper {

    String CHART_URL = "/api/auth/monthly-chart";
    String RESOURCE_URI = "/{guest-house-id}";

    default String getUrl() {
        return CHART_URL;
    }

    default String getURI() {
        return CHART_URL + RESOURCE_URI;
    }
}
