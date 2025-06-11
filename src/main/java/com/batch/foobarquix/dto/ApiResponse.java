package com.batch.foobarquix.dto;

public record ApiResponse<T>(boolean success, String message, T data) {}
