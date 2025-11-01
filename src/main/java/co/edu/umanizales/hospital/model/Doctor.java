package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Doctor class that inherits from Person
 * Demonstrates inheritance, encapsulation, and polymorphism
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends Person implements Registrable, Attendable {
    private String doctorId;
    private String licenseNumber;
    private String specialtyId;
    private String registrationDate;
    private DoctorStatus status;
    private double consultationFee;

    public enum DoctorStatus {
        ACTIVE, INACTIVE, ON_LEAVE
    }

    @Override
    public String getRole() {
        return "DOCTOR";
    }

    @Override
    public String getId() {
        return doctorId;
    }

    @Override
    public void setId(String id) {
        this.doctorId = id;
    }

    @Override
    public String getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String getAttendanceType() {
        return "MEDICAL_CONSULTATION";
    }

    @Override
    public boolean isAvailable() {
        return status == DoctorStatus.ACTIVE;
    }

    /**
     * Polymorphic method: calculate consultation cost based on specialty
     */
    public double calculateConsultationCost() {
        return consultationFee;
    }
}
