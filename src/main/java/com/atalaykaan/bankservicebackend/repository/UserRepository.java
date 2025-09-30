package com.atalaykaan.bankservicebackend.repository;

import com.atalaykaan.bankservicebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
