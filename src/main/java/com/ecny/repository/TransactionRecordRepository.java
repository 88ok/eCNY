package com.ecny.repository;

import com.ecny.entity.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {
    List<TransactionRecord> findTop50ByWalletNoOrderByCreatedAtDesc(String walletNo);
}
