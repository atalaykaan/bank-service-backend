package com.atalaykaan.bankservicebackend.model;

import com.atalaykaan.bankservicebackend.model.enums.Currency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    @Enumerated
    @NotNull
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
