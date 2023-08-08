package com.example.projekt_iddb.models;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DaneOsobowe {
    private String pesel;
    private String imie;
    private String nazwisko;
    private String kodPocztowy;
    private String miasto;
    private String ulica;
    private String telefon;


}
