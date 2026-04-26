package com.ecny.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateWalletRequest(
        @NotBlank(message = "walletNo cannot be blank")
        @Size(max = 64, message = "walletNo length <= 64")
        String walletNo,

        @NotBlank(message = "ownerName cannot be blank")
        @Size(max = 64, message = "ownerName length <= 64")
        String ownerName
) {
}
