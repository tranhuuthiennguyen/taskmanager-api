package com.thiennth.taskmanager.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.thiennth.taskmanager.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 401 — wrong credentials (bad password or user not found)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .statusCode(status)
                        .message(ex.getMessage())
                        .error(status.getReasonPhrase())
                        .build());
    }

    // 401 — user not found during Spring Security auth
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(UsernameNotFoundException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .statusCode(status)
                        .message(ex.getMessage())
                        .error(status.getReasonPhrase())
                        .build());
    }

    // 4xx - business rule violations
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .statusCode(status)
                        .message(ex.getMessage())
                        .error(status.getReasonPhrase())
                        .build());
    }

	@ExceptionHandler(ForbiddenActionException.class)
	public ResponseEntity<ErrorResponse> handleForbiddenAction(ForbiddenActionException ex) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		return ResponseEntity.status(status)
				.body(ErrorResponse.builder()
						.statusCode(status)
						.message(ex.getMessage())
						.error(status.getReasonPhrase())
						.build());
	}

	// 404 Resource not found
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		return ResponseEntity.status(status)
				.body(ErrorResponse.builder()
						.statusCode(status)
						.message(ex.getMessage())
						.error(status.getReasonPhrase())
						.build());
	}

    // 400 - validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> subErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage()).toList();
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .statusCode(status)
                        .message(ex.getMessage())
                        .error(status.getReasonPhrase())
                        .subErrors(subErrors)
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .statusCode(status)
                        .message(ex.getMessage())
                        .error(status.getReasonPhrase())
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Invalid request body";
        Throwable cause = ex.getCause();
        log.debug(cause.getMessage());
        if (cause instanceof InvalidFormatException ife) {
            String field = ife.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
            String invalidValue = String.valueOf(ife.getValue());
            String expectedType = ife.getTargetType().getSimpleName();
            message = "Invalid value '%s' for field '%s', expected type: '%s'"
                .formatted(invalidValue, field, expectedType);
        }
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .statusCode(status)
                        .message(message)
                        .error(status.getReasonPhrase())
                        .build());
    }
    

    // 5xx - database/infrastructure errors
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccess(DataAccessException ex) {
        log.error("Database error: ", ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .statusCode(status)
                        .message("A database error occurred")
                        .error(status.getReasonPhrase())
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.error("Data integrity violation — likely a missing DTO validation: ", ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .statusCode(status)
                        .message("An unexpected error occurred")
                        .error(status.getReasonPhrase())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknown(Exception ex) {
        log.error("Unknown error: ", ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .statusCode(status)
                        .message("An unknown error occurred")
                        .error(status.getReasonPhrase())
                        .build());
    }
}
