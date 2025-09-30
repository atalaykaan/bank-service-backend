package com.atalaykaan.bankservicebackend.dto.request.base;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountRequest {

    @NotNull
    private Long userId;
}
