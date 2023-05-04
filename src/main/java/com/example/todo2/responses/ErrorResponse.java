package com.example.todo2.responses;

import com.example.todo2.common.ErrorCodes;

public record ErrorResponse(String message, ErrorCodes code, Integer status) {
}

