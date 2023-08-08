package com.example.projekt_iddb.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wizyta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pacjent pacjent;

    @ManyToOne
    private Lekarz lekarz;

    private LocalDateTime dataWizyty;
    private int zakonczona;
    private int ePorada;

 }