package com.example.projekt_iddb.controllers;
import com.example.projekt_iddb.models.Lek;
import com.example.projekt_iddb.models.LekNaRecepcie;
import com.example.projekt_iddb.services.LekService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
//wszystko admin
@RestController
@RequestMapping("/leki")
@PreAuthorize("hasRole('ADMIN')")

public class LekController {
    private final LekService lekService;

    public LekController(LekService lekService) {
        this.lekService = lekService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public List<Lek> getAllLeki() {
        return lekService.getAllLeki();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Lek> getLekById(@PathVariable Long id) {
        Optional<Lek> lek = lekService.getLekById(id);
        return lek.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<Lek> createLek(@RequestBody Lek lek) {
        try {
            Lek createdLek = lekService.createLek(lek);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLek);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Lek> updateLek(@PathVariable Long id, @RequestBody Lek updatedLek) {
        try {
            lekService.updateLek(id, updatedLek);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteLekById(@PathVariable Long id) {
        lekService.deleteLekById(id);
        return ResponseEntity.noContent().build();
    }
}
