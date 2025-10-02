package com.atalaykaan.bankservicebackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "account")
    private User user;

    @OneToMany(mappedBy = "account",
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<Wallet> wallets = new ArrayList<>();

    private LocalDateTime createdAt;
}
