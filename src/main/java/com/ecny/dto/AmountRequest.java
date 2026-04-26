package com.ecny.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AmountRequest(
        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.01", message = "amount must be >= 0.01")
        @Digits(integer = 16, fraction = 2, message = "amount max 16 integer and 2 decimal digits")
        BigDecimal amount
) {
}
