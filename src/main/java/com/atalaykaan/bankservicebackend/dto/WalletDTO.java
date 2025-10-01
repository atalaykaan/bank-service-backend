package com.atalaykaan.bankservicebackend.dto;

import com.atalaykaan.bankservicebackend.model.enums.Currency;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class WalletDTO {

    private Long id;

    private Double balance;

    private Currency currency;

    private Long accountDtoId;
}
