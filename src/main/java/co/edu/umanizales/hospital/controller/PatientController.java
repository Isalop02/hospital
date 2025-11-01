package co.edu.umanizales.hospital.controller;

import co.edu.umanizales.hospital.model.Patient;
import co.edu.umanizales.hospital.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Patient operations
 */
@Slf4j
@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Create a new patient
     */
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        try {
            Patient createdPatient = patientService.createPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
        } catch (Exception e) {
            log.error("Error creating patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get patient by ID
     */
    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String patientId) {
        try {
            Patient patient = patientService.getPatientById(patientId);
            if (patient != null) {
                return ResponseEntity.ok(patient);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error retrieving patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all patients
     */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        try {
            List<Patient> patients = patientService.getAllPatients();
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            log.error("Error retrieving all patients", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update patient
     */
    @PutMapping("/{patientId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String patientId, @RequestBody Patient patient) {
        try {
            Patient updatedPatient = patientService.updatePatient(patientId, patient);
            if (updatedPatient != null) {
                return ResponseEntity.ok(updatedPatient);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error updating patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete patient
     */
    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable String patientId) {
        try {
            boolean deleted = patientService.deletePatient(patientId);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error deleting patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
