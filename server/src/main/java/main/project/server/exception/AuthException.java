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
}
