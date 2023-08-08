package com.example.projekt_iddb.repositories;

import com.example.projekt_iddb.models.Recepta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptaRepository extends JpaRepository<Recepta, Long> {
 }