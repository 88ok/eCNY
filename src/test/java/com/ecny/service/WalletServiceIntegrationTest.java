package com.ecny.service;

import com.ecny.dto.AmountRequest;
import com.ecny.dto.CreateWalletRequest;
import com.ecny.dto.TransferRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WalletServiceIntegrationTest {

    @Autowired
    private WalletService walletService;

    @Test
    void shouldCreateDepositAndTransfer() {
        walletService.createWallet(new CreateWalletRequest("W10001", "Alice"));
        walletService.createWallet(new CreateWalletRequest("W10002", "Bob"));

        walletService.deposit("W10001", new AmountRequest(new BigDecimal("100.00")));
        walletService.transfer(new TransferRequest("W10001", "W10002", new BigDecimal("35.50"), "午餐AA"));

        assertThat(walletService.getWallet("W10001").balance()).isEqualByComparingTo("64.50");
        assertThat(walletService.getWallet("W10002").balance()).isEqualByComparingTo("35.50");
        assertThat(walletService.listTransactions("W10001")).hasSize(2);
        assertThat(walletService.listTransactions("W10002")).hasSize(1);
    }
}
