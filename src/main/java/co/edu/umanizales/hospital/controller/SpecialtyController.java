package co.edu.umanizales.hospital.controller;

import co.edu.umanizales.hospital.model.Specialty;
import co.edu.umanizales.hospital.service.SpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/specialties")
@CrossOrigin(origins = "*")
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping
    public ResponseEntity<Specialty> createSpecialty(@RequestBody Specialty specialty) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(specialtyService.createSpecialty(specialty));
        } catch (Exception e) {
            log.error("Error creating specialty", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{specialtyId}")
    public ResponseEntity<Specialty> getSpecialtyById(@PathVariable String specialtyId) {
        try {
            Specialty specialty = specialtyService.getSpecialtyById(specialtyId);
            return specialty != null ? ResponseEntity.ok(specialty) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving specialty", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Specialty>> getAllSpecialties() {
        try {
            return ResponseEntity.ok(specialtyService.getAllSpecialties());
        } catch (Exception e) {
            log.error("Error retrieving all specialties", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{specialtyId}")
    public ResponseEntity<Specialty> updateSpecialty(@PathVariable String specialtyId, @RequestBody Specialty specialty) {
        try {
            Specialty updated = specialtyService.updateSpecialty(specialtyId, specialty);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating specialty", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{specialtyId}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable String specialtyId) {
        try {
            return specialtyService.deleteSpecialty(specialtyId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting specialty", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
