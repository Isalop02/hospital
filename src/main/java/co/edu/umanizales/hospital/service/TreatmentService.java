package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.Treatment;
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
 * Service for managing Treatment operations
 */
@Slf4j
@Service
public class TreatmentService {
    private final CsvService csvService;
    private static final String FILENAME = "treatments";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TreatmentService(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Create a new treatment
     */
    public Treatment createTreatment(Treatment treatment) {
        treatment.setTreatmentId(UUID.randomUUID().toString());
        treatment.setRegistrationDate(LocalDate.now().format(DATE_FORMATTER));
        
        String[] record = treatmentToArray(treatment);
        csvService.appendToCsv(FILENAME, record);
        log.info("Treatment created: {}", treatment.getTreatmentId());
        return treatment;
    }

    /**
     * Get treatment by ID
     */
    public Treatment getTreatmentById(String treatmentId) {
        String[] record = csvService.findRecordById(FILENAME, treatmentId, 0);
        if (record != null) {
            return arrayToTreatment(record);
        }
        return null;
    }

    /**
     * Get all treatments
     */
    public List<Treatment> getAllTreatments() {
        List<String[]> records = csvService.readCsv(FILENAME);
        List<Treatment> treatments = new ArrayList<>();
        
        for (String[] record : records) {
            treatments.add(arrayToTreatment(record));
        }
        return treatments;
    }

    /**
     * Update treatment
     */
    public Treatment updateTreatment(String treatmentId, Treatment treatment) {
        treatment.setTreatmentId(treatmentId);
        String[] record = treatmentToArray(treatment);
        
        if (csvService.updateRecordById(FILENAME, treatmentId, record, 0)) {
            log.info("Treatment updated: {}", treatmentId);
            return treatment;
        }
        return null;
    }

    /**
     * Delete treatment
     */
    public boolean deleteTreatment(String treatmentId) {
        boolean deleted = csvService.deleteRecordById(FILENAME, treatmentId, 0);
        if (deleted) {
            log.info("Treatment deleted: {}", treatmentId);
        }
        return deleted;
    }

    /**
     * Get treatments by patient
     */
    public List<Treatment> getTreatmentsByPatient(String patientId) {
        List<Treatment> allTreatments = getAllTreatments();
        List<Treatment> result = new ArrayList<>();
        
        for (Treatment treatment : allTreatments) {
            if (treatment.getPatient() != null && treatment.getPatient().getPatientId() != null
                && treatment.getPatient().getPatientId().equals(patientId)) {
                result.add(treatment);
            }
        }
        return result;
    }

    /**
     * Convert Treatment object to CSV array
     */
    private String[] treatmentToArray(Treatment treatment) {
        String patientId = treatment.getPatient() != null ? treatment.getPatient().getPatientId() : "";
        String doctorId = treatment.getDoctor() != null ? treatment.getDoctor().getDoctorId() : "";
        String start = treatment.getStartDate() != null ? treatment.getStartDate().format(DATE_FORMATTER) : "";
        String end = treatment.getEndDate() != null ? treatment.getEndDate().format(DATE_FORMATTER) : "";
        return new String[]{
            treatment.getTreatmentId(),
            patientId,
            doctorId,
            treatment.getRegistrationDate(),
            treatment.getDescription() != null ? treatment.getDescription() : "",
            "", // medicines not serialized in CSV for now
            treatment.getStatus() != null ? treatment.getStatus() : "",
            String.valueOf(treatment.getCost()),
            start,
            end
        };
    }

    /**
     * Convert CSV array to Treatment object
     */
    private Treatment arrayToTreatment(String[] record) {
        if (record.length < 10) return null;

        Treatment treatment = new Treatment();
        treatment.setTreatmentId(record[0]);
        if (record[1] != null && !record[1].isEmpty()) {
            Patient p = new Patient();
            p.setPatientId(record[1]);
            treatment.setPatient(p);
        }
        if (record[2] != null && !record[2].isEmpty()) {
            Doctor d = new Doctor();
            d.setDoctorId(record[2]);
            treatment.setDoctor(d);
        }
        treatment.setRegistrationDate(record[3]);
        treatment.setDescription(record[4]);
        // record[5] medicines ignored for now
        treatment.setStatus(record[6]);
        treatment.setCost(!record[7].isEmpty() ? Double.parseDouble(record[7]) : 0.0);
        treatment.setStartDate(!record[8].isEmpty() ? LocalDate.parse(record[8], DATE_FORMATTER) : null);
        treatment.setEndDate(!record[9].isEmpty() ? LocalDate.parse(record[9], DATE_FORMATTER) : null);

        return treatment;
    }
}
