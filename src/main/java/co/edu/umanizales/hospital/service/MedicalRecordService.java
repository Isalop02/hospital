package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.MedicalRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing MedicalRecord operations
 */
@Slf4j
@Service
public class MedicalRecordService {
    private final CsvService csvService;
    private static final String FILENAME = "medical_records";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MedicalRecordService(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Create a new medical record
     */
    public MedicalRecord createMedicalRecord(MedicalRecord record) {
        record.setRecordId(UUID.randomUUID().toString());
        record.setRegistrationDate(LocalDate.now().format(DATE_FORMATTER));
        record.setLastUpdateDate(LocalDate.now().format(DATE_FORMATTER));
        
        String[] csvRecord = medicalRecordToArray(record);
        csvService.appendToCsv(FILENAME, csvRecord);
        log.info("Medical record created: {}", record.getRecordId());
        return record;
    }

    /**
     * Get medical record by ID
     */
    public MedicalRecord getMedicalRecordById(String recordId) {
        String[] record = csvService.findRecordById(FILENAME, recordId, 0);
        if (record != null) {
            return arrayToMedicalRecord(record);
        }
        return null;
    }

    /**
     * Get medical record by patient ID
     */
    public MedicalRecord getMedicalRecordByPatientId(String patientId) {
        List<String[]> records = csvService.readCsv(FILENAME);
        
        for (String[] record : records) {
            if (record.length > 1 && record[1].equals(patientId)) {
                return arrayToMedicalRecord(record);
            }
        }
        return null;
    }

    /**
     * Get all medical records
     */
    public List<MedicalRecord> getAllMedicalRecords() {
        List<String[]> records = csvService.readCsv(FILENAME);
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        
        for (String[] record : records) {
            medicalRecords.add(arrayToMedicalRecord(record));
        }
        return medicalRecords;
    }

    /**
     * Update medical record
     */
    public MedicalRecord updateMedicalRecord(String recordId, MedicalRecord record) {
        record.setRecordId(recordId);
        record.setLastUpdateDate(LocalDate.now().format(DATE_FORMATTER));
        String[] csvRecord = medicalRecordToArray(record);
        
        if (csvService.updateRecordById(FILENAME, recordId, csvRecord, 0)) {
            log.info("Medical record updated: {}", recordId);
            return record;
        }
        return null;
    }

    /**
     * Delete medical record
     */
    public boolean deleteMedicalRecord(String recordId) {
        boolean deleted = csvService.deleteRecordById(FILENAME, recordId, 0);
        if (deleted) {
            log.info("Medical record deleted: {}", recordId);
        }
        return deleted;
    }

    /**
     * Convert MedicalRecord object to CSV array
     */
    private String[] medicalRecordToArray(MedicalRecord record) {
        return new String[]{
            record.getRecordId(),
            record.getPatientId(),
            record.getRegistrationDate(),
            record.getMedicalHistory(),
            record.getAllergies(),
            record.getChronicDiseases(),
            record.getTreatmentIds(),
            record.getLastUpdateDate()
        };
    }

    /**
     * Convert CSV array to MedicalRecord object
     */
    private MedicalRecord arrayToMedicalRecord(String[] record) {
        if (record.length < 8) return null;

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setRecordId(record[0]);
        medicalRecord.setPatientId(record[1]);
        medicalRecord.setRegistrationDate(record[2]);
        medicalRecord.setMedicalHistory(record[3]);
        medicalRecord.setAllergies(record[4]);
        medicalRecord.setChronicDiseases(record[5]);
        medicalRecord.setTreatmentIds(record[6]);
        medicalRecord.setLastUpdateDate(record[7]);

        return medicalRecord;
    }
}
