package main.project.server.helper;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public interface ControllerTestHelper {

    default RequestBuilder getRequestBuilder(String url, long resourceId, MultiValueMap<String, String> queryParams) {
        return get(url, resourceId)
                .params(queryParams)
                .accept(MediaType.APPLICATION_JSON);
    }
}
