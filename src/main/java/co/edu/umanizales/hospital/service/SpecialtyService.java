package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.Specialty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing Specialty operations
 */
@Slf4j
@Service
public class SpecialtyService {
    private final CsvService csvService;
    private static final String FILENAME = "specialties";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public SpecialtyService(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Create a new specialty
     */
    public Specialty createSpecialty(Specialty specialty) {
        specialty.setSpecialtyId(UUID.randomUUID().toString());
        specialty.setRegistrationDate(LocalDate.now().format(DATE_FORMATTER));
        
        String[] record = specialtyToArray(specialty);
        csvService.appendToCsv(FILENAME, record);
        log.info("Specialty created: {}", specialty.getSpecialtyId());
        return specialty;
    }

    /**
     * Get specialty by ID
     */
    public Specialty getSpecialtyById(String specialtyId) {
        String[] record = csvService.findRecordById(FILENAME, specialtyId, 0);
        if (record != null) {
            return arrayToSpecialty(record);
        }
        return null;
    }

    /**
     * Get all specialties
     */
    public List<Specialty> getAllSpecialties() {
        List<String[]> records = csvService.readCsv(FILENAME);
        List<Specialty> specialties = new ArrayList<>();
        
        for (String[] record : records) {
            specialties.add(arrayToSpecialty(record));
        }
        return specialties;
    }

    /**
     * Update specialty
     */
    public Specialty updateSpecialty(String specialtyId, Specialty specialty) {
        specialty.setSpecialtyId(specialtyId);
        String[] record = specialtyToArray(specialty);
        
        if (csvService.updateRecordById(FILENAME, specialtyId, record, 0)) {
            log.info("Specialty updated: {}", specialtyId);
            return specialty;
        }
        return null;
    }

    /**
     * Delete specialty
     */
    public boolean deleteSpecialty(String specialtyId) {
        boolean deleted = csvService.deleteRecordById(FILENAME, specialtyId, 0);
        if (deleted) {
            log.info("Specialty deleted: {}", specialtyId);
        }
        return deleted;
    }

    /**
     * Convert Specialty object to CSV array
     */
    private String[] specialtyToArray(Specialty specialty) {
        return new String[]{
            specialty.getSpecialtyId(),
            specialty.getName(),
            specialty.getDescription(),
            specialty.getRegistrationDate(),
            String.valueOf(specialty.getConsultationCost())
        };
    }

    /**
     * Convert CSV array to Specialty object
     */
    private Specialty arrayToSpecialty(String[] record) {
        if (record.length < 5) return null;

        Specialty specialty = new Specialty();
        specialty.setSpecialtyId(record[0]);
        specialty.setName(record[1]);
        specialty.setDescription(record[2]);
        specialty.setRegistrationDate(record[3]);
        specialty.setConsultationCost(!record[4].isEmpty() ? Double.parseDouble(record[4]) : 0.0);

        return specialty;
    }
}
