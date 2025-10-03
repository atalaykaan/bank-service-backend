package com.atalaykaan.bankservicebackend.dto.response;

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

    private Long accountDtoId;

    private LocalDate birthDate;
}
