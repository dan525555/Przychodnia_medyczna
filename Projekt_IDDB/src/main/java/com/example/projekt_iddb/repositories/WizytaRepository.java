package com.example.projekt_iddb.repositories;

import com.example.projekt_iddb.models.Lekarz;
import com.example.projekt_iddb.models.Pacjent;
import com.example.projekt_iddb.models.Wizyta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WizytaRepository extends JpaRepository<Wizyta, Long> {
    @Query("SELECT w FROM Wizyta w WHERE w.lekarz.id = :lekarzId AND " +
            "(:rodzaj = 3 OR w.zakonczona = :rodzaj)")
    List<Wizyta> findWizytyByLekarzIdAndRodzaj(@Param("lekarzId") Long lekarzId, @Param("rodzaj") int rodzaj);


    @Query("SELECT w FROM Wizyta w WHERE w.pacjent.id = :pacjentId AND " +
            "(:rodzaj = 3 OR w.zakonczona = :rodzaj)")
    List<Wizyta> findWizytyByPacjentIdAndRodzaj(@Param("pacjentId") Long pacjentId, @Param("rodzaj") int rodzaj);

    @Query("SELECT COUNT(w) FROM Wizyta w WHERE w.lekarz = :lekarz AND w.dataWizyty = :dataWizyty AND w.zakonczona = 0")
    long countWizytaByLekarzAndDataWizyty(Lekarz lekarz, LocalDateTime dataWizyty);

    @Query("SELECT COUNT(w) FROM Wizyta w WHERE w.pacjent = :pacjent AND w.dataWizyty >= :startDate AND w.dataWizyty < :endDate AND w.zakonczona = 0")
    long countWizytaByPacjentAndDataWizyty(@Param("pacjent") Pacjent pacjent, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);




}