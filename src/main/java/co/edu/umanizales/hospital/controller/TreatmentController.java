package co.edu.umanizales.hospital.controller;

import co.edu.umanizales.hospital.model.Treatment;
import co.edu.umanizales.hospital.service.TreatmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/treatments")
@CrossOrigin(origins = "*")
public class TreatmentController {
    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @PostMapping
    public ResponseEntity<Treatment> createTreatment(@RequestBody Treatment treatment) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(treatmentService.createTreatment(treatment));
        } catch (Exception e) {
            log.error("Error creating treatment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{treatmentId}")
    public ResponseEntity<Treatment> getTreatmentById(@PathVariable String treatmentId) {
        try {
            Treatment treatment = treatmentService.getTreatmentById(treatmentId);
            return treatment != null ? ResponseEntity.ok(treatment) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving treatment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Treatment>> getAllTreatments() {
        try {
            return ResponseEntity.ok(treatmentService.getAllTreatments());
        } catch (Exception e) {
            log.error("Error retrieving all treatments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{treatmentId}")
    public ResponseEntity<Treatment> updateTreatment(@PathVariable String treatmentId, @RequestBody Treatment treatment) {
        try {
            Treatment updated = treatmentService.updateTreatment(treatmentId, treatment);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating treatment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{treatmentId}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable String treatmentId) {
        try {
            return treatmentService.deleteTreatment(treatmentId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting treatment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Treatment>> getTreatmentsByPatient(@PathVariable String patientId) {
        try {
            return ResponseEntity.ok(treatmentService.getTreatmentsByPatient(patientId));
        } catch (Exception e) {
            log.error("Error retrieving treatments by patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
