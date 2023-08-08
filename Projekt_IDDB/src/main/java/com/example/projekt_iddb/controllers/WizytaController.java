package com.example.projekt_iddb.controllers;
import com.example.projekt_iddb.models.Wizyta;
import com.example.projekt_iddb.repositories.LekarzRepository;
import com.example.projekt_iddb.repositories.PacjentRepository;
import com.example.projekt_iddb.services.WizytaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wizyty")
@PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')or hasRole('PATIENT')")
public class WizytaController {
    private final WizytaService wizytaService;
    private final  LekarzRepository lekarzRepository;
    private final PacjentRepository pacjentRepository;
    public WizytaController(WizytaService wizytaService, LekarzRepository lekarzRepository, PacjentRepository pacjentRepository) {
        this.wizytaService = wizytaService;
        this.lekarzRepository = lekarzRepository;
        this.pacjentRepository = pacjentRepository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')or hasAuthority('doctor:read')")
    public List<Wizyta> getAllWizyty() {
        return wizytaService.getAllWizyty();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')or hasAuthority('doctor:read')")
    public ResponseEntity<Wizyta> getWizytaById(@PathVariable Long id) {
        Optional<Wizyta> wizyta = wizytaService.getWizytaById(id);
        return wizyta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')or hasAuthority('doctor:create')")
    public ResponseEntity<Wizyta> createWizyta(@RequestBody Wizyta wizyta) {
        Wizyta createdWizyta = wizytaService.createWizyta(wizyta);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWizyta);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')or hasAuthority('doctor:update')")
    public ResponseEntity<Wizyta> updateWizyta(@PathVariable Long id, @RequestBody Wizyta wizyta) {
        Wizyta updatedWizyta = wizytaService.updateWizyta(id, wizyta);
        return ResponseEntity.ok(updatedWizyta);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')or hasAuthority('doctor:delete')")
    public ResponseEntity<Void> deleteWizytaById(@PathVariable Long id) {
        wizytaService.deleteWizytaById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lekarz/{id}")
    @PreAuthorize("hasAuthority('admin:read')or hasAuthority('doctor:read')")
    public ResponseEntity<List<Wizyta>> getWizytyByLekarzId(@PathVariable("id") Long lekarzId, @RequestParam("rodzaj") int rodzaj) {
        List<Wizyta> wizyty = wizytaService.getWizytyByLekarzId(lekarzId, rodzaj);
        return new ResponseEntity<>(wizyty, HttpStatus.OK);
    }

    @PatchMapping("/{id}/zakoncz")
    @PreAuthorize("hasAuthority('admin:update')or hasAuthority('doctor:update')")
    public ResponseEntity<Void> odhaczZakonczenieWizyty(@PathVariable Long id) {
        wizytaService.odhaczZakonczenieWizyty(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/odmow")
    @PreAuthorize("hasAuthority('admin:update')or hasAuthority('doctor:update')or hasAuthority('patient:update')")
    public ResponseEntity<Void> odmowWizyte(@PathVariable Long id) {
        wizytaService.odmowWizyte(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/umow")
    @PreAuthorize("hasAuthority('admin:create')or hasAuthority('doctor:create')or hasAuthority('patient:create')")
    public ResponseEntity<Void> umowWizyte(@RequestParam Long pacjentId,
                                           @RequestParam Long lekarzId,
                                           @RequestParam LocalDateTime dataWizyty,
                                           @RequestParam int epo) {
        wizytaService.umowWizyte(pacjentId, lekarzId, dataWizyty, epo ,lekarzRepository, pacjentRepository);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pierwszy-termin")
    @PreAuthorize("hasAuthority('admin:create')or hasAuthority('doctor:create')or hasAuthority('patient:create')")
    public ResponseEntity<Void> pierwszyTermin(@RequestParam Long pacjentId,
                                               @RequestParam Long lekarzId,
                                               @RequestParam int epo) {
        wizytaService.pierwszyTermin(pacjentId, lekarzId, epo,lekarzRepository, pacjentRepository);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lekarz/{id}/wizyty")
    @PreAuthorize("hasAuthority('admin:read')or hasAuthority('doctor:read')")
    public ResponseEntity<List<String>> getWizytyLekarza(@PathVariable("id") Long lekarzId, @RequestParam("rodzaj") int rodzaj) {
        try {
            List<String> wizytyLekarza = wizytaService.wyswietlWizytyLekarza(rodzaj, lekarzId);
            if (wizytyLekarza.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(wizytyLekarza);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/pacjent/{id}/wizyty")
    @PreAuthorize("hasAuthority('admin:read')or hasAuthority('patient:read')")
    public ResponseEntity<List<String>> getWizytyPacjenta(@PathVariable("id") Long pacjentId, @RequestParam("rodzaj") int rodzaj) {
        try {
            List<String> wizytyPacjenta = wizytaService.wyswietlWizytyPacjenta(rodzaj, pacjentId);
            if (wizytyPacjenta.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(wizytyPacjenta);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonList(e.getMessage()));
        }
    }

}
