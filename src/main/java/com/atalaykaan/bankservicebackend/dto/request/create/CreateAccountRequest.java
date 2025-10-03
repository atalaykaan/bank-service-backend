package com.atalaykaan.bankservicebackend.dto.request.create;

import com.atalaykaan.bankservicebackend.dto.request.base.AccountRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateAccountRequest extends AccountRequest {

    @NotNull
    private Long userId;
}
