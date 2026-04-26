package com.ecny.dto;

import com.ecny.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        String walletNo,
        String counterpartyWalletNo,
        TransactionType type,
        BigDecimal amount,
        BigDecimal balanceAfter,
        String note,
        LocalDateTime createdAt
) {
}
