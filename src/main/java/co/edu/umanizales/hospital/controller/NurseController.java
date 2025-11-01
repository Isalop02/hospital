package co.edu.umanizales.hospital.controller;

import co.edu.umanizales.hospital.model.Nurse;
import co.edu.umanizales.hospital.service.NurseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/nurses")
@CrossOrigin(origins = "*")
public class NurseController {
    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @PostMapping
    public ResponseEntity<Nurse> createNurse(@RequestBody Nurse nurse) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(nurseService.createNurse(nurse));
        } catch (Exception e) {
            log.error("Error creating nurse", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{nurseId}")
    public ResponseEntity<Nurse> getNurseById(@PathVariable String nurseId) {
        try {
            Nurse nurse = nurseService.getNurseById(nurseId);
            return nurse != null ? ResponseEntity.ok(nurse) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving nurse", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Nurse>> getAllNurses() {
        try {
            return ResponseEntity.ok(nurseService.getAllNurses());
        } catch (Exception e) {
            log.error("Error retrieving all nurses", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{nurseId}")
    public ResponseEntity<Nurse> updateNurse(@PathVariable String nurseId, @RequestBody Nurse nurse) {
        try {
            Nurse updated = nurseService.updateNurse(nurseId, nurse);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating nurse", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{nurseId}")
    public ResponseEntity<Void> deleteNurse(@PathVariable String nurseId) {
        try {
            return nurseService.deleteNurse(nurseId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting nurse", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/unit/{unit}")
    public ResponseEntity<List<Nurse>> getNursesByUnit(@PathVariable String unit) {
        try {
            return ResponseEntity.ok(nurseService.getNursesByUnit(unit));
        } catch (Exception e) {
            log.error("Error retrieving nurses by unit", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
