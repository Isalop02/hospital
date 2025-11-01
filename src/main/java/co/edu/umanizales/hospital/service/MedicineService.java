package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.Medicine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing Medicine operations
 */
@Slf4j
@Service
public class MedicineService {
    private final CsvService csvService;
    private static final String FILENAME = "medicines";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MedicineService(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Create a new medicine
     */
    public Medicine createMedicine(Medicine medicine) {
        medicine.setMedicineId(UUID.randomUUID().toString());
        medicine.setRegistrationDate(LocalDate.now().format(DATE_FORMATTER));
        
        String[] record = medicineToArray(medicine);
        csvService.appendToCsv(FILENAME, record);
        log.info("Medicine created: {}", medicine.getMedicineId());
        return medicine;
    }

    /**
     * Get medicine by ID
     */
    public Medicine getMedicineById(String medicineId) {
        String[] record = csvService.findRecordById(FILENAME, medicineId, 0);
        if (record != null) {
            return arrayToMedicine(record);
        }
        return null;
    }

    /**
     * Get all medicines
     */
    public List<Medicine> getAllMedicines() {
        List<String[]> records = csvService.readCsv(FILENAME);
        List<Medicine> medicines = new ArrayList<>();
        
        for (String[] record : records) {
            medicines.add(arrayToMedicine(record));
        }
        return medicines;
    }

    /**
     * Update medicine
     */
    public Medicine updateMedicine(String medicineId, Medicine medicine) {
        medicine.setMedicineId(medicineId);
        String[] record = medicineToArray(medicine);
        
        if (csvService.updateRecordById(FILENAME, medicineId, record, 0)) {
            log.info("Medicine updated: {}", medicineId);
            return medicine;
        }
        return null;
    }

    /**
     * Delete medicine
     */
    public boolean deleteMedicine(String medicineId) {
        boolean deleted = csvService.deleteRecordById(FILENAME, medicineId, 0);
        if (deleted) {
            log.info("Medicine deleted: {}", medicineId);
        }
        return deleted;
    }

    /**
     * Get medicines by manufacturer
     */
    public List<Medicine> getMedicinesByManufacturer(String manufacturer) {
        List<Medicine> allMedicines = getAllMedicines();
        List<Medicine> result = new ArrayList<>();
        
        for (Medicine medicine : allMedicines) {
            if (medicine.getManufacturer().equals(manufacturer)) {
                result.add(medicine);
            }
        }
        return result;
    }

    /**
     * Convert Medicine object to CSV array
     */
    private String[] medicineToArray(Medicine medicine) {
        return new String[]{
            medicine.getMedicineId(),
            medicine.getName(),
            medicine.getActiveIngredient(),
            medicine.getRegistrationDate(),
            String.valueOf(medicine.getUnitPrice()),
            String.valueOf(medicine.getStockQuantity()),
            medicine.getManufacturer(),
            medicine.getExpirationDate()
        };
    }

    /**
     * Convert CSV array to Medicine object
     */
    private Medicine arrayToMedicine(String[] record) {
        if (record.length < 8) return null;

        Medicine medicine = new Medicine();
        medicine.setMedicineId(record[0]);
        medicine.setName(record[1]);
        medicine.setActiveIngredient(record[2]);
        medicine.setRegistrationDate(record[3]);
        medicine.setUnitPrice(!record[4].isEmpty() ? Double.parseDouble(record[4]) : 0.0);
        medicine.setStockQuantity(!record[5].isEmpty() ? Integer.parseInt(record[5]) : 0);
        medicine.setManufacturer(record[6]);
        medicine.setExpirationDate(record[7]);

        return medicine;
    }
}
