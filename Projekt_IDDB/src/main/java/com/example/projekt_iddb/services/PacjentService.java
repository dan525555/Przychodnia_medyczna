package com.example.projekt_iddb.services;
import com.example.projekt_iddb.models.DaneOsobowe;
import com.example.projekt_iddb.models.Lekarz;
import com.example.projekt_iddb.models.Pacjent;
import com.example.projekt_iddb.repositories.PacjentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacjentService {
    private final PacjentRepository pacjentRepository;

    public PacjentService(PacjentRepository pacjentRepository) {
        this.pacjentRepository = pacjentRepository;
    }

    public List<Pacjent> getAllPacjenci() {
        return pacjentRepository.findAll();
    }

    public Optional<Pacjent> getPacjentById(Long id) {
        return pacjentRepository.findById(id);
    }

    public Pacjent update(Long id, Pacjent pacjent) {
        Pacjent existingPacjent = pacjentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lekarz o podanym ID nie istnieje."));

         existingPacjent.setDanePacjenta(pacjent.getDanePacjenta());
        existingPacjent.setGrupaKrwi(pacjent.getGrupaKrwi());

        return pacjentRepository.save(existingPacjent);
    }



    public Pacjent createPacjent(Pacjent pacjent) {
        if (!validatePesel(pacjent.getDanePacjenta().getPesel())) {
            throw new IllegalArgumentException("Nieprawidłowy numer PESEL");
        }
       return pacjentRepository.save(pacjent);
    }

    public void deletePacjentById(Long id) {
        pacjentRepository.deleteById(id);
    }


    private boolean validatePesel(String pesel) {
        if (pesel.length() != 11) {
            return false;
        }

        try {
            Long.parseLong(pesel);
        } catch (NumberFormatException e) {
            return false;
        }

        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(pesel.charAt(i)) * weights[i];
        }
        int checksum = 10 - (sum % 10);
        checksum = (checksum == 10) ? 0 : checksum;

        return checksum == Character.getNumericValue(pesel.charAt(10));
    }

}
