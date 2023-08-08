package com.example.projekt_iddb.repositories;

import com.example.projekt_iddb.models.Lekarz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LekarzRepository extends JpaRepository<Lekarz, Long> {
 }