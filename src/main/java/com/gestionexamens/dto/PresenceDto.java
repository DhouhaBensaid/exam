package com.gestionexamens.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresenceDto {
    private Long etudiantId;
    private Long examenId;
    private Long salleId;
    private boolean present;
}
