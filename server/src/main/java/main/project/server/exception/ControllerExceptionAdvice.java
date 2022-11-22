package main.project.server.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 예외 처리 공통화 클래스
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {

    // Controller 클래스에서 발생하는 RequestBody의 유효성 검증
    @ExceptionHandler
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final ErrorResponse response = ErrorResponse.of(exception.getBindingResult());

        return response;
    }

    // URI 변수로 넘어오는 값의 유효성 검증
   @ExceptionHandler
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
       final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

       return response;
    }

    @ExceptionHandler
    public ErrorResponse handleBusinessException(BusinessException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return response;
    }
}
