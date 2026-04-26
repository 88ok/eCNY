package com.ecny.repository;

import com.ecny.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByWalletNo(String walletNo);

    boolean existsByWalletNo(String walletNo);
}
