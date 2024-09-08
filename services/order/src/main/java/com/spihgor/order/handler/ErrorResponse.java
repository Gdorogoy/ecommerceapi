package com.spihgor.order.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errorMessage
) {
}
