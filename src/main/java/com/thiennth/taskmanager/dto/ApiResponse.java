package com.thiennth.taskmanager.dto;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thiennth.taskmanager.filter.CorrelationIdFilter;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    
    private final Boolean   success;
    @JsonProperty("status_code")
    private final int       statusCode;
    private final String    message;
    private final T         data;
    @JsonProperty("correlation_id")
    private final String    correlationId;

    protected ApiResponse(Boolean success, int statusCode, String message, T data) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.correlationId = MDC.get(CorrelationIdFilter.CORRELATION_ID_MDC_KEY);
    }

    public static <T> ApiResponse<T> of(Boolean success, HttpStatus status, String message, T data) {
        return new ApiResponse<T>(success, status.value(), message, data);
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return of(true, HttpStatus.OK, message, data);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return of(true, HttpStatus.CREATED, message, data);
    }
}
