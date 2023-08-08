package com.example.projekt_iddb.controllers;

import com.example.projekt_iddb.models.Recepta;
import com.example.projekt_iddb.repositories.WizytaRepository;
import com.example.projekt_iddb.services.ReceptaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recepty")
@PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')or hasRole('PATIENT')")
public class ReceptaController {
    private final ReceptaService receptaService;
    private final WizytaRepository wizytaRepository;

    public ReceptaController(ReceptaService receptaService, WizytaRepository wizytaRepository) {
        this.receptaService = receptaService;
        this.wizytaRepository = wizytaRepository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')or hasAuthority('doctor:read')")
    public ResponseEntity<List<Recepta>> getAllRecepty() {
        List<Recepta> recepty = receptaService.getAllRecepty();
        return ResponseEntity.ok(recepty);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')or hasAuthority('doctor:read')")
    public ResponseEntity<Recepta> getReceptaById(@PathVariable Long id) {
        Optional<Recepta> recepta = receptaService.getReceptaById(id);
        return recepta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{idWizyty}")
    @PreAuthorize("hasAuthority('admin:create')or hasAuthority('doctor:create')")
    public ResponseEntity<Recepta> createRecepta(@PathVariable Long idWizyty) {
        Recepta createdRecepta = receptaService.createRecepta(idWizyty, wizytaRepository);
        return ResponseEntity.ok(createdRecepta);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')or hasAuthority('doctor:update')")
    public ResponseEntity<Recepta> updateRecepta(@PathVariable Long id, @RequestBody Recepta recepta) {
        Recepta updatedRecepta = receptaService.updateRecepta(id, recepta);
        return ResponseEntity.ok(updatedRecepta);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')or hasAuthority('doctor:delete')")
    public ResponseEntity<Void> deleteReceptaById(@PathVariable Long id) {
        receptaService.deleteReceptaById(id);
        return ResponseEntity.noContent().build();
    }
}
