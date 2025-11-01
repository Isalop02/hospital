package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.MedicalRecord;
import co.edu.umanizales.hospital.model.Patient;
import co.edu.umanizales.hospital.model.Doctor;
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
        if (record.getDate() == null) {
            record.setDate(LocalDate.now());
        }
        
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
        String patientId = record.getPatient() != null ? record.getPatient().getPatientId() : "";
        String doctorId = record.getDoctor() != null ? record.getDoctor().getDoctorId() : "";
        String date = record.getDate() != null ? record.getDate().format(DATE_FORMATTER) : "";
        return new String[]{
            record.getRecordId(),
            patientId,
            doctorId,
            record.getRegistrationDate(),
            date,
            record.getDiagnosis() != null ? record.getDiagnosis() : "",
            record.getTreatment() != null ? record.getTreatment() : "",
            record.getNotes() != null ? record.getNotes() : ""
        };
    }

    /**
     * Convert CSV array to MedicalRecord object
     */
    private MedicalRecord arrayToMedicalRecord(String[] record) {
        if (record.length < 8) return null;

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setRecordId(record[0]);
        if (record[1] != null && !record[1].isEmpty()) {
            Patient p = new Patient();
            p.setPatientId(record[1]);
            medicalRecord.setPatient(p);
        }
        if (record[2] != null && !record[2].isEmpty()) {
            Doctor d = new Doctor();
            d.setDoctorId(record[2]);
            medicalRecord.setDoctor(d);
        }
        medicalRecord.setRegistrationDate(record[3]);
        medicalRecord.setDate(!record[4].isEmpty() ? LocalDate.parse(record[4], DATE_FORMATTER) : null);
        medicalRecord.setDiagnosis(record[5]);
        medicalRecord.setTreatment(record[6]);
        medicalRecord.setNotes(record[7]);

        return medicalRecord;
    }
}
