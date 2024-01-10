package com.capitole.challenge.ecommerce.shared.exceptions;

public record ValidationError(String parameter, String message) {
}
