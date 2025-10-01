package com.atalaykaan.bankservicebackend.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private Long accountDtoId;

    private LocalDate birthDate;
}
