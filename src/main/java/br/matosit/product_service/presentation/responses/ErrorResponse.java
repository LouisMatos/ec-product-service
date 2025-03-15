package br.matosit.product_service.presentation.responses;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final String code;
    private final String message;
    private final List<ValidationError> errors;

    private ErrorResponse(String code, String message) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.message = message;
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message);
    }

    public ErrorResponse addValidationError(String field, String message) {
        this.errors.add(new ValidationError(field, message));
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public static class ValidationError {
        private final String field;
        private final String message;

        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
} 