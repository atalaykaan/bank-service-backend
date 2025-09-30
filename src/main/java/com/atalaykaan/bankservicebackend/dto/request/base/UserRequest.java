package com.atalaykaan.bankservicebackend.dto.request.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {

    @NotBlank
    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @Email
    @NotBlank
    private String email;
}
