package com.thiennth.taskmanager.dto;

import java.util.List;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import com.thiennth.taskmanager.filter.CorrelationIdFilter;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final Boolean       success;
    private final int           statusCode;
    private final String        message;
    private final String        error;
    private final String        correlationId;
    private final List<String>  subErrors;

    public ErrorResponse(Builder builder) {
        this.success = false;
        this.statusCode = builder.statusCode;
        this.message = builder.message;
        this.error = builder.error;
        this.correlationId = MDC.get(CorrelationIdFilter.CORRELATION_ID_MDC_KEY);
        this.subErrors = builder.subErrors;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int statusCode;
        private String message;
        private String error;
        private List<String> subErrors;

        public Builder statusCode(HttpStatus httpStatus) {
            this.statusCode = httpStatus.value();
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder subErrors(List<String> subErrors) {
            this.subErrors = subErrors;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
