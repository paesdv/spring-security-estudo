// UnauthorizedException
package com.estudo.ecommerce.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}