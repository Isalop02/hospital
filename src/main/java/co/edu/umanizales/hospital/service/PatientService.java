package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.Patient;
import co.edu.umanizales.hospital.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing Patient operations
 */
@Slf4j
@Service
public class PatientService {
    private final CsvService csvService;
    private static final String FILENAME = "patients";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PatientService(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Create a new patient
     */
    public Patient createPatient(Patient patient) {
        patient.setPatientId(UUID.randomUUID().toString());
        patient.setRegistrationDate(LocalDate.now().format(DATE_FORMATTER));
        
        String[] record = patientToArray(patient);
        csvService.appendToCsv(FILENAME, record);
        log.info("Patient created: {}", patient.getPatientId());
        return patient;
    }

    /**
     * Get patient by ID
     */
    public Patient getPatientById(String patientId) {
        String[] record = csvService.findRecordById(FILENAME, patientId, 0);
        if (record != null) {
            return arrayToPatient(record);
        }
        return null;
    }

    /**
     * Get all patients
     */
    public List<Patient> getAllPatients() {
        List<String[]> records = csvService.readCsv(FILENAME);
        List<Patient> patients = new ArrayList<>();
        
        for (String[] record : records) {
            patients.add(arrayToPatient(record));
        }
        return patients;
    }

    /**
     * Update patient
     */
    public Patient updatePatient(String patientId, Patient patient) {
        patient.setPatientId(patientId);
        String[] record = patientToArray(patient);
        
        if (csvService.updateRecordById(FILENAME, patientId, record, 0)) {
            log.info("Patient updated: {}", patientId);
            return patient;
        }
        return null;
    }

    /**
     * Delete patient
     */
    public boolean deletePatient(String patientId) {
        boolean deleted = csvService.deleteRecordById(FILENAME, patientId, 0);
        if (deleted) {
            log.info("Patient deleted: {}", patientId);
        }
        return deleted;
    }

    /**
     * Convert Patient object to CSV array
     */
    private String[] patientToArray(Patient patient) {
        return new String[]{
            patient.getPatientId(),
            patient.getIdentification(),
            patient.getFirstName(),
            patient.getLastName(),
            patient.getBirthDate() != null ? patient.getBirthDate().format(DATE_FORMATTER) : "",
            patient.getPhone() != null ? patient.getPhone() : "",
            patient.getEmail() != null ? patient.getEmail() : "",
            patient.getAddress() != null ? patient.getAddress() : "",
            patient.getGender() != null ? patient.getGender().toString() : "",
            patient.getBloodType() != null ? patient.getBloodType() : "",
            patient.getEmergencyContact() != null ? patient.getEmergencyContact() : "",
            patient.getEmergencyPhone() != null ? patient.getEmergencyPhone() : "",
            patient.getRegistrationDate(),
            patient.getStatus() != null ? patient.getStatus().toString() : ""
        };
    }

    /**
     * Convert CSV array to Patient object
     */
    private Patient arrayToPatient(String[] record) {
        if (record.length < 14) return null;

        Patient patient = new Patient();
        patient.setPatientId(record[0]);
        patient.setIdentification(record[1]);
        patient.setFirstName(record[2]);
        patient.setLastName(record[3]);
        patient.setBirthDate(!record[4].isEmpty() ? LocalDate.parse(record[4], DATE_FORMATTER) : null);
        patient.setPhone(record[5]);
        patient.setEmail(record[6]);
        patient.setAddress(record[7]);
        patient.setGender(!record[8].isEmpty() ? Person.Gender.valueOf(record[8]) : null);
        patient.setBloodType(record[9]);
        patient.setEmergencyContact(record[10]);
        patient.setEmergencyPhone(record[11]);
        patient.setRegistrationDate(record[12]);
        patient.setStatus(!record[13].isEmpty() ? Patient.PatientStatus.valueOf(record[13]) : null);

        return patient;
    }
}
