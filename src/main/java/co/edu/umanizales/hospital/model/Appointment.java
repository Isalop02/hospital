package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Appointment class representing a medical appointment
 * Contains relationships with Patient and Doctor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment implements Registrable, Billable {
    private String appointmentId;
    private Patient patient;  // Direct reference to Patient
    private Doctor doctor;    // Direct reference to Doctor
    private LocalDateTime dateTime;
    private String registrationDate;
    private String status;    // Changed from enum to String to match UML
    private String reason;
    private String notes;
    private double cost;

    // Status constants to maintain consistency
    public static final String STATUS_SCHEDULED = "SCHEDULED";
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_NO_SHOW = "NO_SHOW";

    @Override
    public String getId() {
        return appointmentId;
    }

    @Override
    public void setId(String id) {
        this.appointmentId = id;
    }

    @Override
    public String getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public double calculateCost() {
        return cost;
    }

    @Override
    public String getBillingDescription() {
        return "Medical Appointment - " + reason;
    }
}
