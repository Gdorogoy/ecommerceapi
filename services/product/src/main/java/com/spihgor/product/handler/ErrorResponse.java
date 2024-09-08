package com.spihgor.product.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errorMessage
) {
}
