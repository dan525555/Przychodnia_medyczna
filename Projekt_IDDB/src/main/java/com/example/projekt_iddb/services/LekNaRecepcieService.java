package com.example.projekt_iddb.services;
import com.example.projekt_iddb.models.Lek;
import com.example.projekt_iddb.models.LekNaRecepcie;
import com.example.projekt_iddb.models.Recepta;
import com.example.projekt_iddb.repositories.LekNaRecepcieRepository;
import com.example.projekt_iddb.repositories.ReceptaRepository;

import jakarta.transaction.Transactional;
 import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LekNaRecepcieService {
    private final LekNaRecepcieRepository lekNaRecepcieRepository;
      public LekNaRecepcieService(LekNaRecepcieRepository lekNaRecepcieRepository) {
        this.lekNaRecepcieRepository = lekNaRecepcieRepository;
    }

    public List<LekNaRecepcie> getAllLekiNaRecepcie() {
        return lekNaRecepcieRepository.findAll();
    }

    public Optional<LekNaRecepcie> getLekNaRecepcieById(Long id) {
        return lekNaRecepcieRepository.findById(id);
    }

    public LekNaRecepcie createLekNaRecepcie(LekNaRecepcie lekNaRecepcie) {
        return lekNaRecepcieRepository.save(lekNaRecepcie);
    }



    public LekNaRecepcie updateLekNaRecepcie(Long id, LekNaRecepcie updatedLekNaRecepcie) {
        Optional<LekNaRecepcie> lekNaRecepcieOptional = lekNaRecepcieRepository.findById(id);
        if (lekNaRecepcieOptional.isPresent()) {
            LekNaRecepcie lekNaRecepcie = lekNaRecepcieOptional.get();
            lekNaRecepcie.setLek(updatedLekNaRecepcie.getLek());
            lekNaRecepcie.setRecepta(updatedLekNaRecepcie.getRecepta());
            lekNaRecepcie.setDawkowanie(updatedLekNaRecepcie.getDawkowanie());
            return lekNaRecepcieRepository.save(lekNaRecepcie);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LekNaRecepcie o podanym ID nie istnieje.");
        }
    }


    public void deleteLekNaRecepcie(Long id) {
        Optional<LekNaRecepcie> lekNaRecepcieOptional = lekNaRecepcieRepository.findById(id);
        if (lekNaRecepcieOptional.isPresent()) {
            lekNaRecepcieRepository.delete(lekNaRecepcieOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"LekNaRecepcie o podanym ID nie istnieje.");
        }
    }

    @Transactional
    public void dodajLekNaRecepte(Long idRecepta, Lek lek, String dawkowanie, ReceptaRepository receptaRepository) {
        Recepta recepta = receptaRepository.findById(idRecepta)
                .orElseThrow(() -> new IllegalArgumentException("Brak recepty o podanym ID"));

        LekNaRecepcie lekNaRecepcie = new LekNaRecepcie();
        lekNaRecepcie.setRecepta(recepta);
        lekNaRecepcie.setLek(lek);
        lekNaRecepcie.setDawkowanie(dawkowanie);

        lekNaRecepcieRepository.save(lekNaRecepcie);
    }


    public List<String> wyswietlLekiNaRecepcie(Long receptaId, ReceptaRepository receptaRepository) {
        Recepta recepta = receptaRepository.findById(receptaId)
                .orElseThrow(() -> new IllegalArgumentException("Brak recepty o podanym ID"));

        System.out.println("ID recepty: " + receptaId);

        List<LekNaRecepcie> lekiNaRecepcie = lekNaRecepcieRepository.findByRecepta(recepta);
        List<String> lekiNaRecepcieStrings = new ArrayList<>();

        for (LekNaRecepcie lekNaRecepcie : lekiNaRecepcie) {
            System.out.println("ID leku: " + lekNaRecepcie.getLek().getId());
            System.out.println("Nazwa leku: " + lekNaRecepcie.getLek().getNazwaLeku());
            System.out.println("Dawkowanie: " + lekNaRecepcie.getDawkowanie());
            lekiNaRecepcieStrings.add(lekNaRecepcie.toString());
        }

        return lekiNaRecepcieStrings;
    }
}
