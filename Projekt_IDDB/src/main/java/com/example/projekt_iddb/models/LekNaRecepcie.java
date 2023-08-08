package com.example.projekt_iddb.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LekNaRecepcie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lek lek;

    @ManyToOne
    private Recepta recepta;

    private String dawkowanie;

}
