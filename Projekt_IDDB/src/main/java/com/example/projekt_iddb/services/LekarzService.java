package com.example.projekt_iddb.services;

import com.example.projekt_iddb.models.Lekarz;
import com.example.projekt_iddb.repositories.LekarzRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LekarzService {
    private final LekarzRepository lekarzRepository;

    public LekarzService(LekarzRepository lekarzRepository) {
        this.lekarzRepository = lekarzRepository;
    }

    public Lekarz findById(Long id) {
        return lekarzRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lekarz o podanym ID nie istnieje."));
    }

    public Iterable<Lekarz> findAll() {
        return lekarzRepository.findAll();
    }

    public Lekarz save(Lekarz lekarz) {
        return lekarzRepository.save(lekarz);
    }

    public void deleteById(Long id) {
        lekarzRepository.deleteById(id);
    }

    public Lekarz update(Long id, Lekarz lekarz) {
        Lekarz existingLekarz = lekarzRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lekarz o podanym ID nie istnieje."));

         existingLekarz.setSpecjalizacja(lekarz.getSpecjalizacja());
        existingLekarz.getDaneLekarza().setPesel(lekarz.getDaneLekarza().getPesel());

        return lekarzRepository.save(existingLekarz);
    }

/*
    public void dodajLekarza(List<String> specjalizacja, String pesel, String imie, String nazwisko, String kodPocztowy,
                             String miasto, String ulica, String numerTelefonu,
                             LocalTime rozpoczeciePracyPon, LocalTime zakonczeniePracyPon,
                             LocalTime rozpoczeciePracyWt, LocalTime zakonczeniePracyWt,
                             LocalTime rozpoczeciePracySr, LocalTime zakonczeniePracySr,
                             LocalTime rozpoczeciePracyCzw, LocalTime zakonczeniePracyCzw,
                             LocalTime rozpoczeciePracyPt, LocalTime zakonczeniePracyPt) {

        GodzinyPracy godzinyPracy = new GodzinyPracy();
        godzinyPracy.setPonRozp(rozpoczeciePracyPon);
        godzinyPracy.setPonZak(zakonczeniePracyPon);
        godzinyPracy.setWtRozp(rozpoczeciePracyWt);
        godzinyPracy.setWtZak(zakonczeniePracyWt);
        godzinyPracy.setSrRozp(rozpoczeciePracySr);
        godzinyPracy.setSrZak(zakonczeniePracySr);
        godzinyPracy.setCzwRozp(rozpoczeciePracyCzw);
        godzinyPracy.setCzwZak(zakonczeniePracyCzw);
        godzinyPracy.setPtRozp(rozpoczeciePracyPt);
        godzinyPracy.setPtZak(zakonczeniePracyPt);

        DaneOsobowe daneOsobowe = new DaneOsobowe();
        daneOsobowe.setPesel(pesel);
        daneOsobowe.setImie(imie);
        daneOsobowe.setNazwisko(nazwisko);
        daneOsobowe.setKodPocztowy(kodPocztowy);
        daneOsobowe.setMiasto(miasto);
        daneOsobowe.setUlica(ulica);
        daneOsobowe.setTelefon(numerTelefonu);

        Lekarz lekarz = new Lekarz();
        lekarz.setSpecjalizacja(specjalizacja);
        lekarz.setDaneLekarza(daneOsobowe);
        lekarz.setGodzinyPracy(godzinyPracy);
        lekarz.setAktywny(1);

        lekarzRepository.save(lekarz);
    }
    */

    public void zablokujLekarza(Long lekarzId) {
        Optional<Lekarz> lekarzOptional = lekarzRepository.findById(lekarzId);
        if (lekarzOptional.isPresent()) {
            Lekarz lekarz = lekarzOptional.get();
            lekarz.setAktywny(0);
            lekarzRepository.save(lekarz);
        } else {
            throw new EntityNotFoundException("Brak lekarza o podanym ID");
        }
    }

    public void odblokujLekarza(Long lekarzId) {
        Optional<Lekarz> lekarzOptional = lekarzRepository.findById(lekarzId);
        if (lekarzOptional.isPresent()) {
            Lekarz lekarz = lekarzOptional.get();
            lekarz.setAktywny(1);
            lekarzRepository.save(lekarz);
        } else {
            throw new EntityNotFoundException("Brak lekarza o podanym ID");
        }
    }

    public void dodajSpecjalizacje(Long lekarzId, String nowaSpecjalizacja) {
        Optional<Lekarz> lekarzOptional = lekarzRepository.findById(lekarzId);
        if (lekarzOptional.isPresent()) {
            Lekarz lekarz = lekarzOptional.get();
            List<String> specjalizacje = lekarz.getSpecjalizacja();
            specjalizacje.add(nowaSpecjalizacja);
            lekarz.setSpecjalizacja(specjalizacje);
            lekarzRepository.save(lekarz);
        } else {
            throw new EntityNotFoundException("Brak lekarza o podanym ID");
        }
    }


    public String wyswietlGodzinyLekarza(Long idLekarza) {
        Optional<Lekarz> lekarzOptional = lekarzRepository.findById(idLekarza);
        if (lekarzOptional.isEmpty()) {
            throw new IllegalArgumentException("Brak lekarza o podanym ID");
        }

        Lekarz lekarz = lekarzOptional.get();
        StringBuilder sb = new StringBuilder();
        sb.append("Imię i nazwisko: ").append(lekarz.getDaneLekarza().getImie()).append(" ").append(lekarz.getDaneLekarza().getNazwisko()).append("\n");
        sb.append("Poniedziałek:\n");
        sb.append("Początek: ").append(lekarz.getGodzinyPracy().getPonRozp()).append(" Koniec: ").append(lekarz.getGodzinyPracy().getPonZak()).append("\n");
        sb.append("Wtorek:\n");
        sb.append("Początek: ").append(lekarz.getGodzinyPracy().getWtRozp()).append(" Koniec: ").append(lekarz.getGodzinyPracy().getWtZak()).append("\n");
        sb.append("Środa:\n");
        sb.append("Początek: ").append(lekarz.getGodzinyPracy().getSrRozp()).append(" Koniec: ").append(lekarz.getGodzinyPracy().getSrZak()).append("\n");
        sb.append("Czwartek:\n");
        sb.append("Początek: ").append(lekarz.getGodzinyPracy().getCzwRozp()).append(" Koniec: ").append(lekarz.getGodzinyPracy().getCzwZak()).append("\n");
        sb.append("Piątek:\n");
        sb.append("Początek: ").append(lekarz.getGodzinyPracy().getPtRozp()).append(" Koniec: ").append(lekarz.getGodzinyPracy().getPtZak()).append("\n");

        return sb.toString();
    }

    public List<String> wyswietlGodzinyLekarzy() {
        List<Lekarz> lekarze = lekarzRepository.findAll();
        if (lekarze.isEmpty()) {
            throw new IllegalArgumentException("Brak lekarzy");
        }
        List<String> godzinyLekarzy = new ArrayList<>();
        for (Lekarz lekarz : lekarze) {
            godzinyLekarzy.add( wyswietlGodzinyLekarza(lekarz.getId()));
        }
        return godzinyLekarzy;

    }
}
