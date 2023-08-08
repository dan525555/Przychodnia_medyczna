package com.example.projekt_iddb.services;

import com.example.projekt_iddb.models.Lek;
import com.example.projekt_iddb.repositories.LekRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LekService {
    private final LekRepository lekRepository;

    public LekService(LekRepository lekRepository) {
        this.lekRepository = lekRepository;
    }

    public List<Lek> getAllLeki() {
        return lekRepository.findAll();
    }

    public Optional<Lek> getLekById(Long id) {
        return lekRepository.findById(id);
    }

    public Lek createLek(Lek lek) {
         if (lek == null || lek.getNazwaLeku() == null || lek.getNazwaLeku().isEmpty()) {
            throw new IllegalArgumentException("Nazwa leku nie może być pusta.");
        }

        return lekRepository.save(lek);
    }

    @Transactional
    public void updateLek(Long id, Lek updatedLek) {
        Optional<Lek> lekOptional = lekRepository.findById(id);
        if (lekOptional.isPresent()) {
            Lek existingLek = lekOptional.get();

             if (updatedLek == null || updatedLek.getNazwaLeku() == null || updatedLek.getNazwaLeku().isEmpty()) {
                throw new IllegalArgumentException("Nazwa leku nie może być pusta.");
            }

            existingLek.setNazwaLeku(updatedLek.getNazwaLeku());
            lekRepository.save(existingLek);
        } else {
            throw new EntityNotFoundException("Lek o podanym ID nie istnieje.");
        }
    }

    public void deleteLekById(Long id) {
        lekRepository.deleteById(id);
    }
}
