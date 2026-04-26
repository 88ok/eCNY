package com.ecny.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletResponse(
        String walletNo,
        String ownerName,
        BigDecimal balance,
        LocalDateTime createdAt
) {
}
