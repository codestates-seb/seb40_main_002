package main.project.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{
    ExceptionCode exceptionCode;

    public AuthException(ExceptionCode exceptionCode) {
        super(exceptionCode.message);
        this.exceptionCode = exceptionCode;
    }

    public AuthException(String message) {
        super(message);
        this.exceptionCode = ExceptionCode.OTHER_AUTH_EXCEPTION;
        this.exceptionCode.message = message;
    }
}
