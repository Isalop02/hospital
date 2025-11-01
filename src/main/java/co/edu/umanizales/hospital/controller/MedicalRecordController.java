package co.edu.umanizales.hospital.controller;

import co.edu.umanizales.hospital.model.MedicalRecord;
import co.edu.umanizales.hospital.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/medical-records")
@CrossOrigin(origins = "*")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord record) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordService.createMedicalRecord(record));
        } catch (Exception e) {
            log.error("Error creating medical record", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable String recordId) {
        try {
            MedicalRecord record = medicalRecordService.getMedicalRecordById(recordId);
            return record != null ? ResponseEntity.ok(record) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving medical record", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<MedicalRecord> getMedicalRecordByPatientId(@PathVariable String patientId) {
        try {
            MedicalRecord record = medicalRecordService.getMedicalRecordByPatientId(patientId);
            return record != null ? ResponseEntity.ok(record) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving medical record by patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        try {
            return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords());
        } catch (Exception e) {
            log.error("Error retrieving all medical records", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable String recordId, @RequestBody MedicalRecord record) {
        try {
            MedicalRecord updated = medicalRecordService.updateMedicalRecord(recordId, record);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating medical record", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String recordId) {
        try {
            return medicalRecordService.deleteMedicalRecord(recordId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting medical record", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
