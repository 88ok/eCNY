package com.ecny.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TransferRequest(
        @NotBlank(message = "fromWalletNo cannot be blank")
        @Size(max = 64)
        String fromWalletNo,

        @NotBlank(message = "toWalletNo cannot be blank")
        @Size(max = 64)
        String toWalletNo,

        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.01", message = "amount must be >= 0.01")
        @Digits(integer = 16, fraction = 2)
        BigDecimal amount,

        @Size(max = 120)
        String note
) {
}
