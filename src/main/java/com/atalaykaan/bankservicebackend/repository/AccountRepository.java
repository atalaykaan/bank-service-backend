package com.atalaykaan.bankservicebackend.repository;

import com.atalaykaan.bankservicebackend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
