package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.Appointment;
import co.edu.umanizales.hospital.model.Doctor;
import co.edu.umanizales.hospital.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing Appointment operations
 */
@Slf4j
@Service
public class AppointmentService {
    private final CsvService csvService;
    private static final String FILENAME = "appointments";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AppointmentService(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Create a new appointment
     */
    public Appointment createAppointment(Appointment appointment) {
        appointment.setAppointmentId(UUID.randomUUID().toString());
        appointment.setRegistrationDate(LocalDate.now().format(DATE_FORMATTER));
        
        String[] record = appointmentToArray(appointment);
        csvService.appendToCsv(FILENAME, record);
        log.info("Appointment created: {}", appointment.getAppointmentId());
        return appointment;
    }

    /**
     * Get appointment by ID
     */
    public Appointment getAppointmentById(String appointmentId) {
        String[] record = csvService.findRecordById(FILENAME, appointmentId, 0);
        if (record != null) {
            return arrayToAppointment(record);
        }
        return null;
    }

    /**
     * Get all appointments
     */
    public List<Appointment> getAllAppointments() {
        List<String[]> records = csvService.readCsv(FILENAME);
        List<Appointment> appointments = new ArrayList<>();
        
        for (String[] record : records) {
            appointments.add(arrayToAppointment(record));
        }
        return appointments;
    }

    /**
     * Update appointment
     */
    public Appointment updateAppointment(String appointmentId, Appointment appointment) {
        appointment.setAppointmentId(appointmentId);
        String[] record = appointmentToArray(appointment);
        
        if (csvService.updateRecordById(FILENAME, appointmentId, record, 0)) {
            log.info("Appointment updated: {}", appointmentId);
            return appointment;
        }
        return null;
    }

    /**
     * Delete appointment
     */
    public boolean deleteAppointment(String appointmentId) {
        boolean deleted = csvService.deleteRecordById(FILENAME, appointmentId, 0);
        if (deleted) {
            log.info("Appointment deleted: {}", appointmentId);
        }
        return deleted;
    }

    /**
     * Get appointments by patient
     */
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        List<Appointment> allAppointments = getAllAppointments();
        List<Appointment> result = new ArrayList<>();
        
        for (Appointment appointment : allAppointments) {
            if (appointment.getPatient() != null && appointment.getPatient().getPatientId() != null
                && appointment.getPatient().getPatientId().equals(patientId)) {
                result.add(appointment);
            }
        }
        return result;
    }

    /**
     * Get appointments by doctor
     */
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        List<Appointment> allAppointments = getAllAppointments();
        List<Appointment> result = new ArrayList<>();
        
        for (Appointment appointment : allAppointments) {
            if (appointment.getDoctor() != null && appointment.getDoctor().getDoctorId() != null
                && appointment.getDoctor().getDoctorId().equals(doctorId)) {
                result.add(appointment);
            }
        }
        return result;
    }

    /**
     * Convert Appointment object to CSV array
     */
    private String[] appointmentToArray(Appointment appointment) {
        String patientId = appointment.getPatient() != null ? appointment.getPatient().getPatientId() : "";
        String doctorId = appointment.getDoctor() != null ? appointment.getDoctor().getDoctorId() : "";
        String dt = appointment.getDateTime() != null ? appointment.getDateTime().format(DATETIME_FORMATTER) : "";
        return new String[]{
            appointment.getAppointmentId(),
            patientId,
            doctorId,
            dt,
            appointment.getRegistrationDate(),
            appointment.getStatus() != null ? appointment.getStatus() : "",
            appointment.getReason() != null ? appointment.getReason() : "",
            appointment.getNotes() != null ? appointment.getNotes() : "",
            String.valueOf(appointment.getCost())
        };
    }

    /**
     * Convert CSV array to Appointment object
     */
    private Appointment arrayToAppointment(String[] record) {
        if (record.length < 9) return null;

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(record[0]);
        if (record[1] != null && !record[1].isEmpty()) {
            Patient p = new Patient();
            p.setPatientId(record[1]);
            appointment.setPatient(p);
        }
        if (record[2] != null && !record[2].isEmpty()) {
            Doctor d = new Doctor();
            d.setDoctorId(record[2]);
            appointment.setDoctor(d);
        }
        appointment.setDateTime(!record[3].isEmpty() ? LocalDateTime.parse(record[3], DATETIME_FORMATTER) : null);
        appointment.setRegistrationDate(record[4]);
        appointment.setStatus(record[5]);
        appointment.setReason(record[6]);
        appointment.setNotes(record[7]);
        appointment.setCost(!record[8].isEmpty() ? Double.parseDouble(record[8]) : 0.0);

        return appointment;
    }
}
