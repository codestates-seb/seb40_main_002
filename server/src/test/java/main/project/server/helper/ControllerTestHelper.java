package main.project.server.helper;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public interface ControllerTestHelper<T> {

    default RequestBuilder postRequestBuilder(String url,
                                              String content) {
        return  post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
    }

    default RequestBuilder getRequestBuilder(String url, long resourceId, MultiValueMap<String, String> queryParams) {
        return get(url, resourceId)
                .params(queryParams)
                .accept(MediaType.APPLICATION_JSON);
    }

    default String toJsonContent(T t) {
        Gson gson = new Gson();
        String content = gson.toJson(t);
        return content;
    }
}
