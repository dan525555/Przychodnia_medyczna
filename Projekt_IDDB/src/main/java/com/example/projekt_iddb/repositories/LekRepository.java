package com.example.projekt_iddb.repositories;

import com.example.projekt_iddb.models.Lek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LekRepository extends JpaRepository<Lek, Long> {
 }