package main.project.server.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private List<FieldErrorResponse> fieldErrors;
    private List<ConstraintViolationErrorResponse> violationErrors;


    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(FieldErrorResponse.of(bindingResult), null);
    }

    public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
        return new ErrorResponse(null, ConstraintViolationErrorResponse.of(violations));
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class FieldErrorResponse {
        private String field;
        private Object rejectedValue;
        private String reason;


        public static List<FieldErrorResponse> of(BindingResult bindingResult) {
            final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream()
                    .map(error -> FieldErrorResponse.builder()
                            .field(error.getField())
                            .rejectedValue(error.getRejectedValue() == null ? "" : error.getRejectedValue().toString())
                            .reason(error.getDefaultMessage())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ConstraintViolationErrorResponse {
        private String propertyPath;
        private String rejectedValue;
        private String reason;

        public static List<ConstraintViolationErrorResponse> of(
                Set<ConstraintViolation<?>> constraintViolations) {
            return constraintViolations.stream()
                    .map(constraintViolation ->
                            ConstraintViolationErrorResponse.builder()
                                    .propertyPath(constraintViolation.getPropertyPath().toString())
                                    .rejectedValue(constraintViolation.getInvalidValue().toString())
                                    .reason(constraintViolation.getMessage())
                                    .build())
                    .collect(Collectors.toList());
        }
    }
}