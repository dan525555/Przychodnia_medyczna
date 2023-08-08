package com.example.projekt_iddb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lekarz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> specjalizacja;

    @Embedded
    private DaneOsobowe daneLekarza;

    @Embedded
    private GodzinyPracy godzinyPracy;

    private int aktywny;

}