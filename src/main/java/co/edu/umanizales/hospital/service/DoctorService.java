package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.Doctor;
import co.edu.umanizales.hospital.model.Person;
import co.edu.umanizales.hospital.model.Specialty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for managing Doctor operations
 */
@Slf4j
@Service
public class DoctorService {
    private final CsvService csvService;
    private static final String FILENAME = "doctors";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DoctorService(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Create a new doctor
     */
    public Doctor createDoctor(Doctor doctor) {
        doctor.setDoctorId(UUID.randomUUID().toString());
        doctor.setRegistrationDate(LocalDate.now().format(DATE_FORMATTER));
        
        String[] record = doctorToArray(doctor);
        csvService.appendToCsv(FILENAME, record);
        log.info("Doctor created: {}", doctor.getDoctorId());
        return doctor;
    }

    /**
     * Get doctor by ID
     */
    public Doctor getDoctorById(String doctorId) {
        String[] record = csvService.findRecordById(FILENAME, doctorId, 0);
        if (record != null) {
            return arrayToDoctor(record);
        }
        return null;
    }

    /**
     * Get all doctors
     */
    public List<Doctor> getAllDoctors() {
        List<String[]> records = csvService.readCsv(FILENAME);
        List<Doctor> doctors = new ArrayList<>();
        
        for (String[] record : records) {
            doctors.add(arrayToDoctor(record));
        }
        return doctors;
    }

    /**
     * Update doctor
     */
    public Doctor updateDoctor(String doctorId, Doctor doctor) {
        doctor.setDoctorId(doctorId);
        String[] record = doctorToArray(doctor);
        
        if (csvService.updateRecordById(FILENAME, doctorId, record, 0)) {
            log.info("Doctor updated: {}", doctorId);
            return doctor;
        }
        return null;
    }

    /**
     * Delete doctor
     */
    public boolean deleteDoctor(String doctorId) {
        boolean deleted = csvService.deleteRecordById(FILENAME, doctorId, 0);
        if (deleted) {
            log.info("Doctor deleted: {}", doctorId);
        }
        return deleted;
    }

    /**
     * Get doctors by specialty
     */
    public List<Doctor> getDoctorsBySpecialty(String specialtyName) {
        List<Doctor> allDoctors = getAllDoctors();
        List<Doctor> result = new ArrayList<>();
        
        for (Doctor doctor : allDoctors) {
            if (doctor.getSpecialties() != null && specialtyName != null) {
                boolean match = doctor.getSpecialties().stream()
                    .map(Specialty::getName)
                    .anyMatch(name -> specialtyName.equalsIgnoreCase(name));
                if (match) {
                    result.add(doctor);
                }
            }
        }
        return result;
    }

    /**
     * Convert Doctor object to CSV array
     */
    private String[] doctorToArray(Doctor doctor) {
        String specialties = doctor.getSpecialties() != null
            ? doctor.getSpecialties().stream().map(Specialty::getName).collect(Collectors.joining("|"))
            : "";
        return new String[]{
            doctor.getDoctorId(),
            doctor.getIdentification(),
            doctor.getFirstName(),
            doctor.getLastName(),
            doctor.getBirthDate() != null ? doctor.getBirthDate().format(DATE_FORMATTER) : "",
            doctor.getPhone() != null ? doctor.getPhone() : "",
            doctor.getEmail() != null ? doctor.getEmail() : "",
            doctor.getAddress() != null ? doctor.getAddress() : "",
            doctor.getGender() != null ? doctor.getGender().toString() : "",
            doctor.getBloodType() != null ? doctor.getBloodType() : "",
            doctor.getLicenseNumber(),
            specialties,
            doctor.getRegistrationDate(),
            doctor.getStatus() != null ? doctor.getStatus().toString() : "",
            String.valueOf(doctor.getConsultationFee())
        };
    }

    /**
     * Convert CSV array to Doctor object
     */
    private Doctor arrayToDoctor(String[] record) {
        if (record.length < 15) return null;

        Doctor doctor = new Doctor();
        doctor.setDoctorId(record[0]);
        doctor.setIdentification(record[1]);
        doctor.setFirstName(record[2]);
        doctor.setLastName(record[3]);
        doctor.setBirthDate(!record[4].isEmpty() ? LocalDate.parse(record[4], DATE_FORMATTER) : null);
        doctor.setPhone(record[5]);
        doctor.setEmail(record[6]);
        doctor.setAddress(record[7]);
        doctor.setGender(!record[8].isEmpty() ? Person.Gender.valueOf(record[8]) : null);
        doctor.setBloodType(record[9]);
        doctor.setLicenseNumber(record[10]);
        if (record[11] != null && !record[11].isEmpty()) {
            String[] names = record[11].split("\\|");
            List<Specialty> specs = new ArrayList<>();
            for (String n : names) {
                Specialty s = new Specialty();
                s.setName(n);
                specs.add(s);
            }
            doctor.setSpecialties(specs);
        }
        doctor.setRegistrationDate(record[12]);
        doctor.setStatus(!record[13].isEmpty() ? Doctor.DoctorStatus.valueOf(record[13]) : null);
        doctor.setConsultationFee(!record[14].isEmpty() ? Double.parseDouble(record[14]) : 0.0);

        return doctor;
    }
}
