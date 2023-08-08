package com.example.projekt_iddb.repositories;

import com.example.projekt_iddb.models.LekNaRecepcie;
import com.example.projekt_iddb.models.Recepta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LekNaRecepcieRepository extends JpaRepository<LekNaRecepcie, Long> {

 List<LekNaRecepcie> findByRecepta(Recepta recepta);

 }