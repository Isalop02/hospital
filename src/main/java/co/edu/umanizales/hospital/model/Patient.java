package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Patient class that inherits from Person
 * Demonstrates inheritance and encapsulation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends Person implements Registrable {
    private String patientId;
    private String emergencyContact;
    private String emergencyPhone;
    private String registrationDate;
    private PatientStatus status;
    // According to UML
    private String medicalHistory;
    private String allergies;
    private String currentMedication;
    private List<Appointment> appointments;   // 1..* with Appointment
    private List<MedicalRecord> medicalRecords; // 1..* with MedicalRecord
    private Room assignedRoom;               // 0..1 relationship with Room

    public enum PatientStatus {
        ACTIVE, INACTIVE, DISCHARGED
    }

    @Override
    public String getRole() {
        return "PATIENT";
    }

    @Override
    public String getId() {
        return patientId;
    }

    @Override
    public void setId(String id) {
        this.patientId = id;
    }

    @Override
    public String getRegistrationDate() {
        return registrationDate;
    }
}
