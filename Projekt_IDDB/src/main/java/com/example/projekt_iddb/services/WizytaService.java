package com.example.projekt_iddb.services;

import com.example.projekt_iddb.models.GodzinyPracy;
import com.example.projekt_iddb.models.Lekarz;
import com.example.projekt_iddb.models.Pacjent;
import com.example.projekt_iddb.models.Wizyta;

import com.example.projekt_iddb.repositories.LekarzRepository;
import com.example.projekt_iddb.repositories.PacjentRepository;
import com.example.projekt_iddb.repositories.WizytaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WizytaService {
    private final WizytaRepository wizytaRepository;

    public WizytaService(WizytaRepository wizytaRepository) {
        this.wizytaRepository = wizytaRepository;
    }

    public List<Wizyta> getAllWizyty() {
        return wizytaRepository.findAll();
    }

    public Optional<Wizyta> getWizytaById(Long id) {
        return wizytaRepository.findById(id);
    }

    public Wizyta createWizyta(Wizyta wizyta) {
        return wizytaRepository.save(wizyta);
    }

    public Wizyta updateWizyta(Long id, Wizyta updatedWizyta) {
        Optional<Wizyta> wizytaOptional = wizytaRepository.findById(id);
        if (wizytaOptional.isPresent()) {
            Wizyta wizyta = wizytaOptional.get();
            wizyta.setPacjent(updatedWizyta.getPacjent());
            wizyta.setLekarz(updatedWizyta.getLekarz());
            wizyta.setDataWizyty(updatedWizyta.getDataWizyty());
            wizyta.setZakonczona(updatedWizyta.getZakonczona());
            wizyta.setEPorada(updatedWizyta.getEPorada());
            return wizytaRepository.save(wizyta);
        } else {
            throw new IllegalArgumentException("Wizyta o podanym ID nie istnieje.");
        }
    }

    public void deleteWizytaById(Long id) {
        wizytaRepository.deleteById(id);
    }

    public List<Wizyta> getWizytyByLekarzId(Long lekarzId, int rodzaj) {
        return wizytaRepository.findWizytyByLekarzIdAndRodzaj(lekarzId, rodzaj);
    }

    public void odhaczZakonczenieWizyty(Long wizytaId) {
        Wizyta wizyta = wizytaRepository.findById(wizytaId)
                .orElseThrow(() -> new EntityNotFoundException("Wizyta o podanym ID nie istnieje."));

        wizyta.setZakonczona(1);
        wizytaRepository.save(wizyta);
    }

    public List<String>wyswietlWizytyLekarza(int rodzaj, long lekarzId) {
        if (rodzaj != 0 && rodzaj != 1 && rodzaj != 2 && rodzaj != 3) {
            throw new IllegalArgumentException("Niepoprawny rodzaj wizyty do pokazania przekazany w parametrze. Poprawne wybory 0, 1 lub 2.");
        }


        List<Wizyta> wizyty = wizytaRepository.findWizytyByLekarzIdAndRodzaj(lekarzId, rodzaj);
        List<String> wizytyLekarza = new ArrayList<>();

        if (wizyty.size() != 0)
            for (Wizyta wizyta : wizyty) {
                StringBuilder sb = new StringBuilder();
                if (wizyta.getZakonczona() == 0) {
                    sb.append("ZAPLANOWANA WIZYTA:\n");
                } else if (wizyta.getZakonczona() == 1) {
                    sb.append("ZAKONCZONE WIZYTA:\n");
                } else if (wizyta.getZakonczona() == 2) {
                    sb.append("ODWOLANA:\n");
                }
                sb.append(printWizytaDetails(wizyta));
                wizytyLekarza .add(sb.toString());
            }
        return wizytyLekarza;
    }

    public List<String> wyswietlWizytyPacjenta(int rodzaj, long pacjentId) {
        if (rodzaj != 0 && rodzaj != 1 && rodzaj != 2 && rodzaj != 3) {
            throw new IllegalArgumentException("Niepoprawny rodzaj wizyty do pokazania przekazany w parametrze. Poprawne wybory 0, 1 lub 2.");
        }


        List<Wizyta> wizyty = wizytaRepository.findWizytyByPacjentIdAndRodzaj(pacjentId, rodzaj);
        List<String> wizytyPacjenta = new ArrayList<>();

        if (wizyty.size() != 0)

            for (Wizyta wizyta : wizyty) {
                StringBuilder sb = new StringBuilder();
                if (wizyta.getZakonczona() == 0) {
                    sb.append("ZAPLANOWANA WIZYTA:\n");
                } else if (wizyta.getZakonczona() == 1) {
                    sb.append("ZAKONCZONE WIZYTA:\n");
                } else if (wizyta.getZakonczona() == 2) {
                    sb.append("ODWOLANA:\n");
                }
                sb.append(printWizytaDetails(wizyta));
                wizytyPacjenta.add(sb.toString());
            }
            return wizytyPacjenta;
    }

    private String printWizytaDetails(Wizyta wizyta) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        sb.append("Id wizyty: " + wizyta.getId()+"\n");
        sb.append("Data wizyty: " + wizyta.getDataWizyty().format(formatter)+"\n");
        if (wizyta.getEPorada() == 1)
            sb.append("E-porada: Tak\n");
        else
            sb.append("E-porada: Nie\n");
        return sb.toString();
    }

    @Transactional
    public void odmowWizyte(Long wizytaId) {
        Wizyta wizyta = wizytaRepository.findById(wizytaId)
                .orElseThrow(() -> new EntityNotFoundException("Wizyta o podanym ID nie istnieje."));

        wizyta.setZakonczona(2);
        wizytaRepository.save(wizyta);
    }


    public void umowWizyte(Long pacjentId, Long lekarzId, LocalDateTime dataWizyty, int epo, LekarzRepository lekarzRepository, PacjentRepository pacjentRepository) {
        Lekarz lekarz = lekarzRepository.findById(lekarzId)
                .orElseThrow(() -> new IllegalArgumentException("Brak Lekarza o podanym ID"));

        Pacjent pacjent = pacjentRepository.findById(pacjentId)
                .orElseThrow(() -> new IllegalArgumentException("Brak pacjenta o podanym ID"));



        if (lekarz.getAktywny()==0) {
            throw new IllegalArgumentException("Lekarz jest zablokowany");
        }


        // Sprawdzenie poprawności wartości parametru epo
        if (epo != 0 && epo != 1) {
            throw new IllegalArgumentException("Zla opcja e-porady");
        }

        LocalDate dzisiejszaData = LocalDate.now();

// Sprawdzenie czy data wizyty jest wcześniejsza niż dzisiejsza data
        if (dataWizyty.toLocalDate().isBefore(dzisiejszaData)||dataWizyty.toLocalDate().equals(dzisiejszaData)) {
            throw new IllegalArgumentException("Wizyty mogą być umawiane tylko na co najmniej przyszły dzień.");
        }

        // Sprawdzenie czy wizyta nie została zaplanowana na dzień wolny (sobota lub niedziela)
        if (dataWizyty.getDayOfWeek() == DayOfWeek.SATURDAY || dataWizyty.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("W weekend nieczynne");
        }

        // Sprawdzenie czy lekarz ma dostępność na podaną godzinę
        LocalTime godzina = dataWizyty.toLocalTime();
         LocalTime godzinaRozpoczecia =  getGodzinyPracyDlaDniaTygodnia(dataWizyty.getDayOfWeek(), lekarz, 0);
        LocalTime godzinaZakonczenia =  getGodzinyPracyDlaDniaTygodnia(dataWizyty.getDayOfWeek(), lekarz, 1);

        if (godzina.isBefore(godzinaRozpoczecia) || godzina.compareTo(godzinaZakonczenia) >= 0) {
            throw new IllegalArgumentException("Lekarz nie pracuje w tej godzinie");
        }

        LocalDateTime startDate = dataWizyty.toLocalDate().atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(1);


         long liczbaWizytWPodanymDniu = wizytaRepository.countWizytaByPacjentAndDataWizyty(pacjent, startDate,endDate);
        if (liczbaWizytWPodanymDniu > 0) {
            throw new IllegalArgumentException("Masz już umówioną wizytę w tym dniu");
        }

        // Sprawdzenie czy lekarz nie ma już umówionej wizyty na podaną godzinę
        long liczbaWizytNaPodanaGodzine = wizytaRepository.countWizytaByLekarzAndDataWizyty(lekarz, dataWizyty);
        if (liczbaWizytNaPodanaGodzine > 0) {
            throw new IllegalArgumentException("Termin jest już zajęty");
        }

        // Sprawdzenie czy minuty są podzielne przez 20: 0, 20, 40
        if (godzina.getMinute() % 20 != 0) {
            throw new IllegalArgumentException("Podana godzina jest niepoprawna");
        }

        // Utworzenie nowej wizyty
        Wizyta nowaWizyta = new Wizyta();
        nowaWizyta.setPacjent(pacjent);
        nowaWizyta.setLekarz(lekarz);
        nowaWizyta.setDataWizyty(dataWizyty);
        nowaWizyta.setZakonczona(0);
        nowaWizyta.setEPorada(epo);

        Wizyta zapisanaWizyta = createWizyta(nowaWizyta);

        // Ustawienie wartości new_wizyta_id na ID zapisanej wizyty
        Long new_wizyta_id = zapisanaWizyta.getId();

        // Wylogowanie komunikatu, że wizyta została utworzona
        System.out.println("Utworzono wizytę o ID: " + new_wizyta_id);
    }

    public void pierwszyTermin(Long pacjentId, Long lekarzId, int epo, LekarzRepository lekarzRepository, PacjentRepository pacjentRepository) {
        LocalDate dataWizyty = LocalDate.now().plusDays(1);
        int tester = 0;
        int liczba = 0;
        int godzina = -1;
        int minuty = -1;

        // Pobranie lekarza i sprawdzenie, czy jest aktywny
        Lekarz lekarz = lekarzRepository.findById(lekarzId)
                .orElseThrow(() -> new IllegalArgumentException("Brak lekarza o podanym ID"));

        if (lekarz.getAktywny() == 0) {
            throw new IllegalArgumentException("Lekarz jest zablokowany");
        }

        // Pobranie pacjenta i sprawdzenie, czy istnieje
        Pacjent pacjent = pacjentRepository.findById(pacjentId)
                .orElseThrow(() -> new IllegalArgumentException("Brak pacjenta o podanym ID"));

        // Sprawdzenie poprawności wartości parametru epo
        if (epo != 0 && epo != 1) {
            throw new IllegalArgumentException("Zła opcja e-porady");
        }

        // Iteracyjne szukanie pierwszego wolnego terminu
        while (tester == 0) {
            if (dataWizyty.getDayOfWeek() == DayOfWeek.SATURDAY) {
                dataWizyty = dataWizyty.plusDays(2);
            } else if (dataWizyty.getDayOfWeek() == DayOfWeek.SUNDAY) {
                dataWizyty = dataWizyty.plusDays(1);
            }

            LocalTime godzinyPracyRozp = getGodzinyPracyDlaDniaTygodnia(dataWizyty.getDayOfWeek(), lekarz,0);
            LocalTime godzinyPracyZak= getGodzinyPracyDlaDniaTygodnia(dataWizyty.getDayOfWeek(), lekarz,1);

            if (godzina == -1) {
                godzina = godzinyPracyRozp.getHour();
                minuty = 0;
            }
            LocalDateTime startDate = dataWizyty.atStartOfDay();
            LocalDateTime endDate = startDate.plusDays(1);
            // Sprawdzenie czy pacjent ma już umówioną wizytę w tym dniu
            long liczbaWizytWPodanymDniu = wizytaRepository.countWizytaByPacjentAndDataWizyty(pacjent, startDate, endDate);
            if (liczbaWizytWPodanymDniu > 0) {
                dataWizyty = dataWizyty.plusDays(1);
                godzina = -1;
                continue;
            }

            // Sprawdzenie czy lekarz ma już umówioną wizytę na podaną godzinę
            long liczbaWizytNaPodanaGodzine = wizytaRepository.countWizytaByLekarzAndDataWizyty(lekarz, dataWizyty.atTime(godzina, minuty));
            if (liczbaWizytNaPodanaGodzine > 0) {
                minuty += 20;
                if (minuty == 60) {
                    minuty = 0;
                    godzina++;
                }

                if (godzina == godzinyPracyZak.getHour()&&minuty== godzinyPracyZak.getMinute()) {
                    dataWizyty = dataWizyty.plusDays(1);
                    godzina = -1;
                }
                continue;
            }

            if (liczbaWizytNaPodanaGodzine == 0) {
                tester = 1;
            }
        }

        LocalDateTime dataWizytyFinal = dataWizyty.atTime(godzina, minuty);

        // Utworzenie nowej wizyty
        Wizyta nowaWizyta = new Wizyta();
        nowaWizyta.setPacjent(pacjent);
        nowaWizyta.setLekarz(lekarz);
        nowaWizyta.setDataWizyty(dataWizytyFinal);
        nowaWizyta.setZakonczona(0);
        nowaWizyta.setEPorada(epo);

        Wizyta zapisanaWizyta = createWizyta(nowaWizyta);;
        Long new_wizyta_id = zapisanaWizyta.getId();

        System.out.println("Utworzono wizytę o ID: " + new_wizyta_id);

    }
















        private LocalTime getGodzinyPracyDlaDniaTygodnia(DayOfWeek dzienTygodnia, Lekarz lekarz, int kiedy) {
        switch (dzienTygodnia) {
            case MONDAY:
                return (kiedy == 0) ? lekarz.getGodzinyPracy().getPonRozp() : lekarz.getGodzinyPracy().getPonZak();
            case TUESDAY:
                return (kiedy == 0) ? lekarz.getGodzinyPracy().getWtRozp() : lekarz.getGodzinyPracy().getWtZak();
            case WEDNESDAY:
                return (kiedy == 0) ? lekarz.getGodzinyPracy().getSrRozp() : lekarz.getGodzinyPracy().getSrZak();
            case THURSDAY:
                return (kiedy == 0) ? lekarz.getGodzinyPracy().getCzwRozp() : lekarz.getGodzinyPracy().getCzwZak();
            case FRIDAY:
                return (kiedy == 0) ? lekarz.getGodzinyPracy().getPtRozp() : lekarz.getGodzinyPracy().getPtZak();
            default:
                throw new IllegalArgumentException("Nieobsługiwany dzień tygodnia");
        }
    }
}
