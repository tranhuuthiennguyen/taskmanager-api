package com.thiennth.taskmanager.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class PaginatedResponse<T> extends ApiResponse<List<T>> {

    private final int page;
    private final int limit;
    private final long total;
    @JsonProperty("total_pages")
    private final int totalPages;
    @JsonProperty("has_next")
    private final boolean hasNext;
    @JsonProperty("has_previous")
    private final boolean hasPrevious;

    private PaginatedResponse(Boolean success, int statusCode, String message, List<T> data,
                                int page, int limit, long total) {
        super(success, statusCode, message, data);
        this.page = page;
        this.limit = limit;
        this.total = total;
        this.totalPages = (int) Math.ceil((double) total / limit);
        this.hasNext = page < this.totalPages;
        this.hasPrevious = page > 1;
    }
    
    public static <T> PaginatedResponse<T> of(List<T> data, int page, int limit, long total) {
        return new PaginatedResponse<>(
            true, HttpStatus.OK.value(), "Success", data, page, limit, total
        );
    }

    public static <T> PaginatedResponse<T> of(String message, List<T> data, int page, int limit, long total) {
        return new PaginatedResponse<>(
            true, HttpStatus.OK.value(), message, data, page, limit, total
        );
    }
}
