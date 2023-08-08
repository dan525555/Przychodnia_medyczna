package com.example.projekt_iddb.controllers;
import com.example.projekt_iddb.models.Lekarz;
import com.example.projekt_iddb.models.Pacjent;
import com.example.projekt_iddb.services.PacjentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacjenci")
@PreAuthorize("hasRole('ADMIN')or hasRole('PATIENT')")
public class PacjentController {
    private final PacjentService pacjentService;

    public PacjentController(PacjentService pacjentService) {
        this.pacjentService = pacjentService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') ")
    public List<Pacjent> getAllPacjenci() {
        return pacjentService.getAllPacjenci();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') ")
    public ResponseEntity<Pacjent> getPacjentById(@PathVariable Long id) {
        Optional<Pacjent> pacjent = pacjentService.getPacjentById(id);
        return pacjent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')or hasAuthority('patient:create')")
    public ResponseEntity<Pacjent> createPacjent(@RequestBody Pacjent pacjent) {
        Pacjent createdPacjent = pacjentService.createPacjent(pacjent);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPacjent);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update') ")
    public ResponseEntity<Pacjent> updatePacjent(@PathVariable("id") Long id, @RequestBody Pacjent pacjent) {
        Pacjent updatedPacjent = pacjentService.update(id, pacjent);
        return ResponseEntity.ok(updatedPacjent);
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete') ")
    public ResponseEntity<Void> deletePacjentById(@PathVariable Long id) {
        pacjentService.deletePacjentById(id);
        return ResponseEntity.noContent().build();
    }
}
