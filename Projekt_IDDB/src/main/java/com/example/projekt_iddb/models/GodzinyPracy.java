package com.example.projekt_iddb.models;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GodzinyPracy {
    private LocalTime ponRozp;
    private LocalTime ponZak;
    private LocalTime wtRozp;
    private LocalTime wtZak;
    private LocalTime srRozp;
    private LocalTime srZak;
    private LocalTime czwRozp;
    private LocalTime czwZak;
    private LocalTime ptRozp;
    private LocalTime ptZak;
}
