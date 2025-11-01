package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
