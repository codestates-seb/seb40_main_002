package main.project.server.exception;


import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    ExceptionCode exceptionCode;

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.message);
        this.exceptionCode = exceptionCode;
    }
}
