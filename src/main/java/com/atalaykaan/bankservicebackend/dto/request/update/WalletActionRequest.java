package com.atalaykaan.bankservicebackend.dto.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletActionRequest {

    @NotNull
    private Double amount;
}