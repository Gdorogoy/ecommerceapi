package com.spihgor.customer.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errorMessage
) {
}

