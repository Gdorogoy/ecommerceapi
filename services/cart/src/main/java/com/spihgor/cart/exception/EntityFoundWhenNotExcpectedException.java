package com.spihgor.cart.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityFoundWhenNotExcpectedException extends RuntimeException {
    private final String msg;
}
