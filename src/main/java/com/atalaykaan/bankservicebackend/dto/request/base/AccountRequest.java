package com.atalaykaan.bankservicebackend.dto.request.base;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountRequest {

    @NotBlank
    @Email
    private String email;
}
