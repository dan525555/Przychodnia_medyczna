package com.example.projekt_iddb.controllers;

import com.example.projekt_iddb.models.Lekarz;
import com.example.projekt_iddb.services.LekarzService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/lekarze")
@PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')or hasRole('PATIENT')")
public class LekarzController {
    private final LekarzService lekarzService;

    public LekarzController(LekarzService lekarzService) {
        this.lekarzService = lekarzService;
    }
     @GetMapping("/{id}")
     @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Lekarz> getLekarzById(@PathVariable("id") Long id) {
        Lekarz lekarz = lekarzService.findById(id);
        return ResponseEntity.ok(lekarz);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Iterable<Lekarz>> getAllLekarze() {
        Iterable<Lekarz> lekarze = lekarzService.findAll();
        return ResponseEntity.ok(lekarze);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")

    public ResponseEntity<Lekarz> createLekarz(@RequestBody Lekarz lekarz) {
        Lekarz createdLekarz = lekarzService.save(lekarz);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLekarz);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Lekarz> updateLekarz(@PathVariable("id") Long id, @RequestBody Lekarz lekarz) {
        Lekarz updatedLekarz = lekarzService.update(id, lekarz);
        return ResponseEntity.ok(updatedLekarz);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete') ")

    public ResponseEntity<Void> deleteLekarz(@PathVariable("id") Long id) {
        lekarzService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/zablokuj")
    @PreAuthorize("hasAuthority('admin:update')or hasAuthority('doctor:update')")
    public ResponseEntity<Void> zablokujLekarza(@PathVariable("id") Long id) {
        lekarzService.zablokujLekarza(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/odblokuj")
    @PreAuthorize("hasAuthority('admin:update')or hasAuthority('doctor:update')")
    public ResponseEntity<Void> odblokujLekarza(@PathVariable("id") Long id) {
        lekarzService.odblokujLekarza(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/specjalizacje")
    @PreAuthorize("hasAuthority('admin:update')or hasAuthority('doctor:update')")
    public ResponseEntity<Void> dodajSpecjalizacje(@PathVariable("id") Long id, @RequestBody String specjalizacja) {
        lekarzService.dodajSpecjalizacje(id, specjalizacja);
        return ResponseEntity.ok().build();
    }
     @GetMapping("/{id}/godziny")
     @PreAuthorize("hasAuthority('admin:read')or hasAuthority('doctor:read')or hasAuthority('patient:read')")
     public ResponseEntity<String> wyswietlGodzinyLekarza(@PathVariable("id") Long id) {
        String godzinyLekarza = lekarzService.wyswietlGodzinyLekarza(id);
        return ResponseEntity.ok(godzinyLekarza);
    }

    @GetMapping("/godziny")
    @PreAuthorize("hasAuthority('admin:read')or hasAuthority('doctor:read')or hasAuthority('patient:read')")
    public ResponseEntity<List<String>> wyswietlGodzinyLekarzy() {
        List<String> godzinyLekarzy = lekarzService.wyswietlGodzinyLekarzy();
        return ResponseEntity.ok(godzinyLekarzy);
    }
}

