package com.ecny.service;

import com.ecny.dto.AmountRequest;
import com.ecny.dto.CreateWalletRequest;
import com.ecny.dto.TransactionResponse;
import com.ecny.dto.TransferRequest;
import com.ecny.dto.WalletResponse;
import com.ecny.entity.TransactionRecord;
import com.ecny.entity.TransactionType;
import com.ecny.entity.Wallet;
import com.ecny.exception.BusinessException;
import com.ecny.repository.TransactionRecordRepository;
import com.ecny.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    public WalletService(WalletRepository walletRepository, TransactionRecordRepository transactionRecordRepository) {
        this.walletRepository = walletRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request) {
        if (walletRepository.existsByWalletNo(request.walletNo())) {
            throw new BusinessException("walletNo already exists: " + request.walletNo());
        }

        Wallet wallet = new Wallet();
        wallet.setWalletNo(request.walletNo());
        wallet.setOwnerName(request.ownerName());
        wallet.setBalance(BigDecimal.ZERO);

        Wallet saved = walletRepository.save(wallet);
        return toWalletResponse(saved);
    }

    @Transactional(readOnly = true)
    public WalletResponse getWallet(String walletNo) {
        Wallet wallet = findWallet(walletNo);
        return toWalletResponse(wallet);
    }

    @Transactional
    public WalletResponse deposit(String walletNo, AmountRequest request) {
        Wallet wallet = findWallet(walletNo);
        wallet.setBalance(wallet.getBalance().add(request.amount()));

        Wallet saved = walletRepository.save(wallet);
        saveRecord(saved.getWalletNo(), null, TransactionType.DEPOSIT, request.amount(), saved.getBalance(), "充值");
        return toWalletResponse(saved);
    }

    @Transactional
    public void transfer(TransferRequest request) {
        if (request.fromWalletNo().equals(request.toWalletNo())) {
            throw new BusinessException("fromWalletNo and toWalletNo cannot be same");
        }

        Wallet fromWallet = findWallet(request.fromWalletNo());
        Wallet toWallet = findWallet(request.toWalletNo());

        if (fromWallet.getBalance().compareTo(request.amount()) < 0) {
            throw new BusinessException("insufficient balance");
        }

        fromWallet.setBalance(fromWallet.getBalance().subtract(request.amount()));
        toWallet.setBalance(toWallet.getBalance().add(request.amount()));

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        String note = request.note() == null || request.note().isBlank() ? "转账" : request.note();
        saveRecord(fromWallet.getWalletNo(), toWallet.getWalletNo(), TransactionType.TRANSFER_OUT, request.amount(), fromWallet.getBalance(), note);
        saveRecord(toWallet.getWalletNo(), fromWallet.getWalletNo(), TransactionType.TRANSFER_IN, request.amount(), toWallet.getBalance(), note);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> listTransactions(String walletNo) {
        findWallet(walletNo);
        return transactionRecordRepository.findTop50ByWalletNoOrderByCreatedAtDesc(walletNo)
                .stream()
                .map(this::toTransactionResponse)
                .toList();
    }

    private Wallet findWallet(String walletNo) {
        return walletRepository.findByWalletNo(walletNo)
                .orElseThrow(() -> new BusinessException("wallet not found: " + walletNo));
    }

    private void saveRecord(String walletNo,
                            String counterpartyWalletNo,
                            TransactionType type,
                            BigDecimal amount,
                            BigDecimal balanceAfter,
                            String note) {
        TransactionRecord record = new TransactionRecord();
        record.setWalletNo(walletNo);
        record.setCounterpartyWalletNo(counterpartyWalletNo);
        record.setType(type);
        record.setAmount(amount);
        record.setBalanceAfter(balanceAfter);
        record.setNote(note);
        transactionRecordRepository.save(record);
    }

    private WalletResponse toWalletResponse(Wallet wallet) {
        return new WalletResponse(wallet.getWalletNo(), wallet.getOwnerName(), wallet.getBalance(), wallet.getCreatedAt());
    }

    private TransactionResponse toTransactionResponse(TransactionRecord record) {
        return new TransactionResponse(
                record.getWalletNo(),
                record.getCounterpartyWalletNo(),
                record.getType(),
                record.getAmount(),
                record.getBalanceAfter(),
                record.getNote(),
                record.getCreatedAt()
        );
    }
}
