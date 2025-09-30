package com.atalaykaan.bankservicebackend.repository;

import com.atalaykaan.bankservicebackend.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
