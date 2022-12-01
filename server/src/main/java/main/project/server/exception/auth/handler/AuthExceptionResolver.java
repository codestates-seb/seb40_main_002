package main.project.server.exception.auth.handler;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import main.project.server.exception.AuthException;
import main.project.server.exception.ErrorResponse;
import main.project.server.exception.ExceptionCode;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthExceptionResolver {
    private final Gson gson;
    private String contentType = "application/json;charset=UTF-8";

    public void handleException(RuntimeException exception, HttpServletResponse response) throws IOException {

        if(exception instanceof AuthException)
        {
            AuthException authException = (AuthException) exception;
            sendErrorResponse(response, authException.getExceptionCode());
        }
        else
        {
            throw exception;
        }

    }

    private void sendErrorResponse(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {

        ErrorResponse errorResponse = ErrorResponse.of(exceptionCode);
        String content = gson.toJson(errorResponse);

        response.setContentType(contentType);
        response.setStatus(exceptionCode.getCode());
        response.getWriter().write(content);
    }
}
