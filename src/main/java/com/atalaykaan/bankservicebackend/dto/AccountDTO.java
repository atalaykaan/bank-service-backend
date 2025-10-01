package com.atalaykaan.bankservicebackend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class AccountDTO {

    private Long id;

    private Long userDtoId;

    private List<Long> walletIdList;

    private LocalDateTime createdAt;
}
