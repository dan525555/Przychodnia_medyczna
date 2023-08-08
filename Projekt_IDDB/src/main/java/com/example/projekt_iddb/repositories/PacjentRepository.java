package com.example.projekt_iddb.repositories;

import com.example.projekt_iddb.models.Pacjent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacjentRepository extends JpaRepository<Pacjent, Long> {
 }