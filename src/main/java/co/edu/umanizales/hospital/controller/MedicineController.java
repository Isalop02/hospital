package co.edu.umanizales.hospital.controller;

import co.edu.umanizales.hospital.model.Medicine;
import co.edu.umanizales.hospital.service.MedicineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*")
public class MedicineController {
    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(medicineService.createMedicine(medicine));
        } catch (Exception e) {
            log.error("Error creating medicine", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{medicineId}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable String medicineId) {
        try {
            Medicine medicine = medicineService.getMedicineById(medicineId);
            return medicine != null ? ResponseEntity.ok(medicine) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving medicine", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        try {
            return ResponseEntity.ok(medicineService.getAllMedicines());
        } catch (Exception e) {
            log.error("Error retrieving all medicines", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{medicineId}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable String medicineId, @RequestBody Medicine medicine) {
        try {
            Medicine updated = medicineService.updateMedicine(medicineId, medicine);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating medicine", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{medicineId}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable String medicineId) {
        try {
            return medicineService.deleteMedicine(medicineId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting medicine", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/manufacturer/{manufacturer}")
    public ResponseEntity<List<Medicine>> getMedicinesByManufacturer(@PathVariable String manufacturer) {
        try {
            return ResponseEntity.ok(medicineService.getMedicinesByManufacturer(manufacturer));
        } catch (Exception e) {
            log.error("Error retrieving medicines by manufacturer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
