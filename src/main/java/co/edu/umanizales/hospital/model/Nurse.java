package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Nurse class that inherits from Person
 * Demonstrates inheritance, encapsulation, and polymorphism
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nurse extends Person implements Registrable, Attendable {
    private String nurseId;
    private String licenseNumber;
    private String registrationDate;
    private NurseStatus status;
    private String assignedUnit;
    private double hourlyRate;
    private List<Specialty> specializations; // UML: Nurse has many specializations

    public enum NurseStatus {
        ACTIVE, INACTIVE, ON_LEAVE
    }

    @Override
    public String getRole() {
        return "NURSE";
    }

    @Override
    public String getId() {
        return nurseId;
    }

    @Override
    public void setId(String id) {
        this.nurseId = id;
    }

    @Override
    public String getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String getAttendanceType() {
        return "NURSING_CARE";
    }

    @Override
    public boolean isAvailable() {
        return status == NurseStatus.ACTIVE;
    }

    /**
     * Polymorphic method: calculate nursing cost based on hours
     */
    public double calculateNursingCost(int hours) {
        return hourlyRate * hours;
    }
}
