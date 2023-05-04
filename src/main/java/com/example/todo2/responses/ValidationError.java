package com.example.todo2.responses;

import com.example.todo2.common.ErrorCodes;

import java.util.List;

public record ValidationError(String message, ErrorCodes code, Integer status, List<FieldError> fieldErrors) {
}
