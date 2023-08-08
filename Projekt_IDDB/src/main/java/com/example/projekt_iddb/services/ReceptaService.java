package com.example.projekt_iddb.services;

import com.example.projekt_iddb.models.Recepta;
import com.example.projekt_iddb.models.Wizyta;
import com.example.projekt_iddb.repositories.ReceptaRepository;
import com.example.projekt_iddb.repositories.WizytaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReceptaService {
    private final ReceptaRepository receptaRepository;

    public ReceptaService(ReceptaRepository receptaRepository) {
        this.receptaRepository = receptaRepository;
     }

    public List<Recepta> getAllRecepty() {
        return receptaRepository.findAll();
    }

    public Optional<Recepta> getReceptaById(Long id) {
        return receptaRepository.findById(id);
    }

    public Recepta createRecepta(Long idWizyty,WizytaRepository wizytaRepository) {
        Optional<Wizyta> optionalWizyta = wizytaRepository.findById(idWizyty);
        Wizyta wizyta = optionalWizyta.orElseThrow(() -> new IllegalArgumentException("Wizyta o podanym ID nie istnieje"));

        Recepta recepta = new Recepta();
        recepta.setDataReal(null);
        recepta.setDataWyst(LocalDateTime.now());
        recepta.setDataWazn(LocalDateTime.now().plusDays(30));
        recepta.setWizyta(wizyta);

        return receptaRepository.save(recepta);
    }

    public Recepta updateRecepta(Long id, Recepta updatedRecepta) {
        Optional<Recepta> receptaOptional = receptaRepository.findById(id);
        if (receptaOptional.isPresent()) {
            Recepta recepta = receptaOptional.get();
            recepta.setWizyta(updatedRecepta.getWizyta());
            recepta.setDataWyst(updatedRecepta.getDataWyst());
            recepta.setDataReal(updatedRecepta.getDataReal());
            recepta.setDataWazn(updatedRecepta.getDataWazn());
            return receptaRepository.save(recepta);
        } else {
            throw new IllegalArgumentException("Recepta o podanym ID nie istnieje.");
        }
    }

    public void deleteReceptaById(Long id) {
        receptaRepository.deleteById(id);
    }
}
