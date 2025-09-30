package com.atalaykaan.bankservicebackend.dto.request.base;

import com.atalaykaan.bankservicebackend.model.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletRequest {

    private Long id;

    private Double balance;

    @NotNull
    private Currency currency;

    @NotNull
    private Long accountId;
}
