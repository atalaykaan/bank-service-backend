package com.atalaykaan.bankservicebackend.dto;

import com.atalaykaan.bankservicebackend.model.enums.Currency;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AccountDTO {

    private Long id;

    private Double balance;

    private Currency currency;
}
