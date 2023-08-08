package com.example.projekt_iddb.controllers;
import com.example.projekt_iddb.models.LekNaRecepcie;
import com.example.projekt_iddb.repositories.ReceptaRepository;
import com.example.projekt_iddb.services.LekNaRecepcieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/leki-na-recepcie")
@PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
public class LekNaRecepcieController {
    private final LekNaRecepcieService lekNaRecepcieService;
    private final ReceptaRepository receptaRepository;
    public LekNaRecepcieController(LekNaRecepcieService lekNaRecepcieService, ReceptaRepository receptaRepository) {
        this.lekNaRecepcieService = lekNaRecepcieService;
        this.receptaRepository = receptaRepository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')or hasAuthority('doctor:read')")
    public ResponseEntity<List<LekNaRecepcie>> getAllLekiNaRecepcie() {
        List<LekNaRecepcie> lekiNaRecepcieList = lekNaRecepcieService.getAllLekiNaRecepcie();
        return new ResponseEntity<>(lekiNaRecepcieList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')or hasAuthority('doctor:read')")

    public ResponseEntity<LekNaRecepcie> getLekNaRecepcieById(@PathVariable("id") Long id) {
        LekNaRecepcie lekNaRecepcie = lekNaRecepcieService.getLekNaRecepcieById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LekNaRecepcie o podanym ID nie istnieje."));
        return new ResponseEntity<>(lekNaRecepcie, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')or hasAuthority('doctor:create')")
    public ResponseEntity<LekNaRecepcie> createLekNaRecepcie(@RequestBody LekNaRecepcie lekNaRecepcie) {
        LekNaRecepcie createdLekNaRecepcie = lekNaRecepcieService.createLekNaRecepcie(lekNaRecepcie);
        return new ResponseEntity<>(createdLekNaRecepcie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')or hasAuthority('doctor:update')")
    public ResponseEntity<LekNaRecepcie> updateLekNaRecepcie(@PathVariable("id") Long id, @RequestBody LekNaRecepcie lekNaRecepcie) {
        LekNaRecepcie updatedLekNaRecepcie = lekNaRecepcieService.updateLekNaRecepcie(id, lekNaRecepcie);
        return new ResponseEntity<>(updatedLekNaRecepcie, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')or hasAuthority('doctor:delete')")
    public ResponseEntity<Void> deleteLekNaRecepcie(@PathVariable("id") Long id) {
        lekNaRecepcieService.deleteLekNaRecepcie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{idRecepta}")
    @PreAuthorize("hasAuthority('admin:create')or hasAuthority('doctor:create')")
    public ResponseEntity<Void> dodajLekNaRecepte(@PathVariable("idRecepta") Long idRecepta, @RequestBody LekNaRecepcie lekNaRecepcie) {
        lekNaRecepcieService.dodajLekNaRecepte(idRecepta, lekNaRecepcie.getLek(), lekNaRecepcie.getDawkowanie(), receptaRepository);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/wyswietl/{receptaId}")
    @PreAuthorize("hasAuthority('admin:read')or hasAuthority('doctor:read')")
    public ResponseEntity<List<String>> wyswietlLekiNaRecepcie(@PathVariable("receptaId") Long receptaId) {
        List<String> lekiNaRecepcie = lekNaRecepcieService.wyswietlLekiNaRecepcie(receptaId, receptaRepository);
        return ResponseEntity.ok(lekiNaRecepcie);
    }

}
