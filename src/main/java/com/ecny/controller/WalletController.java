package com.ecny.controller;

import com.ecny.dto.AmountRequest;
import com.ecny.dto.CreateWalletRequest;
import com.ecny.dto.TransactionResponse;
import com.ecny.dto.TransferRequest;
import com.ecny.dto.WalletResponse;
import com.ecny.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WalletResponse createWallet(@Valid @RequestBody CreateWalletRequest request) {
        return walletService.createWallet(request);
    }

    @GetMapping("/{walletNo}")
    public WalletResponse getWallet(@PathVariable String walletNo) {
        return walletService.getWallet(walletNo);
    }

    @PostMapping("/{walletNo}/deposit")
    public WalletResponse deposit(@PathVariable String walletNo, @Valid @RequestBody AmountRequest request) {
        return walletService.deposit(walletNo, request);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transfer(@Valid @RequestBody TransferRequest request) {
        walletService.transfer(request);
    }

    @GetMapping("/{walletNo}/transactions")
    public List<TransactionResponse> listTransactions(@PathVariable String walletNo) {
        return walletService.listTransactions(walletNo);
    }
}
