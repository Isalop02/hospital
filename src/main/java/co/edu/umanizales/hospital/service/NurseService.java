package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.Nurse;
import co.edu.umanizales.hospital.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing Nurse operations
 */
@Slf4j
@Service
public class NurseService {
    private final CsvService csvService;
    private static final String FILENAME = "nurses";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public NurseService(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Create a new nurse
     */
    public Nurse createNurse(Nurse nurse) {
        nurse.setNurseId(UUID.randomUUID().toString());
        nurse.setRegistrationDate(LocalDate.now().format(DATE_FORMATTER));
        
        String[] record = nurseToArray(nurse);
        csvService.appendToCsv(FILENAME, record);
        log.info("Nurse created: {}", nurse.getNurseId());
        return nurse;
    }

    /**
     * Get nurse by ID
     */
    public Nurse getNurseById(String nurseId) {
        String[] record = csvService.findRecordById(FILENAME, nurseId, 0);
        if (record != null) {
            return arrayToNurse(record);
        }
        return null;
    }

    /**
     * Get all nurses
     */
    public List<Nurse> getAllNurses() {
        List<String[]> records = csvService.readCsv(FILENAME);
        List<Nurse> nurses = new ArrayList<>();
        
        for (String[] record : records) {
            nurses.add(arrayToNurse(record));
        }
        return nurses;
    }

    /**
     * Update nurse
     */
    public Nurse updateNurse(String nurseId, Nurse nurse) {
        nurse.setNurseId(nurseId);
        String[] record = nurseToArray(nurse);
        
        if (csvService.updateRecordById(FILENAME, nurseId, record, 0)) {
            log.info("Nurse updated: {}", nurseId);
            return nurse;
        }
        return null;
    }

    /**
     * Delete nurse
     */
    public boolean deleteNurse(String nurseId) {
        boolean deleted = csvService.deleteRecordById(FILENAME, nurseId, 0);
        if (deleted) {
            log.info("Nurse deleted: {}", nurseId);
        }
        return deleted;
    }

    /**
     * Get nurses by assigned unit
     */
    public List<Nurse> getNursesByUnit(String unit) {
        List<Nurse> allNurses = getAllNurses();
        List<Nurse> result = new ArrayList<>();
        
        for (Nurse nurse : allNurses) {
            if (nurse.getAssignedUnit().equals(unit)) {
                result.add(nurse);
            }
        }
        return result;
    }

    /**
     * Convert Nurse object to CSV array
     */
    private String[] nurseToArray(Nurse nurse) {
        return new String[]{
            nurse.getNurseId(),
            nurse.getIdentification(),
            nurse.getFirstName(),
            nurse.getLastName(),
            nurse.getBirthDate() != null ? nurse.getBirthDate().format(DATE_FORMATTER) : "",
            nurse.getPhone() != null ? nurse.getPhone() : "",
            nurse.getEmail() != null ? nurse.getEmail() : "",
            nurse.getAddress() != null ? nurse.getAddress() : "",
            nurse.getGender() != null ? nurse.getGender().toString() : "",
            nurse.getBloodType() != null ? nurse.getBloodType() : "",
            nurse.getLicenseNumber(),
            nurse.getRegistrationDate(),
            nurse.getStatus() != null ? nurse.getStatus().toString() : "",
            nurse.getAssignedUnit(),
            String.valueOf(nurse.getHourlyRate())
        };
    }

    /**
     * Convert CSV array to Nurse object
     */
    private Nurse arrayToNurse(String[] record) {
        if (record.length < 15) return null;

        Nurse nurse = new Nurse();
        nurse.setNurseId(record[0]);
        nurse.setIdentification(record[1]);
        nurse.setFirstName(record[2]);
        nurse.setLastName(record[3]);
        nurse.setBirthDate(!record[4].isEmpty() ? LocalDate.parse(record[4], DATE_FORMATTER) : null);
        nurse.setPhone(record[5]);
        nurse.setEmail(record[6]);
        nurse.setAddress(record[7]);
        nurse.setGender(!record[8].isEmpty() ? Person.Gender.valueOf(record[8]) : null);
        nurse.setBloodType(record[9]);
        nurse.setLicenseNumber(record[10]);
        nurse.setRegistrationDate(record[11]);
        nurse.setStatus(!record[12].isEmpty() ? Nurse.NurseStatus.valueOf(record[12]) : null);
        nurse.setAssignedUnit(record[13]);
        nurse.setHourlyRate(!record[14].isEmpty() ? Double.parseDouble(record[14]) : 0.0);

        return nurse;
    }
}
