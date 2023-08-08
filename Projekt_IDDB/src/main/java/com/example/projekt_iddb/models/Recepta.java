package com.example.projekt_iddb.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recepta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Wizyta wizyta;

    private LocalDateTime dataWyst;
    private LocalDateTime dataReal;
    private LocalDateTime dataWazn;

 }