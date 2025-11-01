package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Appointment class demonstrating composition
 * Contains references to Patient and Doctor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment implements Registrable, Billable {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDateTime appointmentDateTime;
    private String registrationDate;
    private AppointmentStatus status;
    private String reason;
    private String notes;
    private double cost;

    public enum AppointmentStatus {
        SCHEDULED, COMPLETED, CANCELLED, NO_SHOW
    }

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
