package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Treatment class demonstrating composition and aggregation
 * Contains references to medicines and procedures
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Treatment implements Registrable, Billable {
    private String treatmentId;
    private Patient patient;                 // UML: belongs to Patient
    private Doctor doctor;                   // UML: prescribed by Doctor
    private MedicalRecord medicalRecord;     // UML: documents a MedicalRecord
    private String registrationDate;
    private String description;
    private List<Medicine> medicines;        // UML: contains medicines
    private String status;                   // UML string status
    private double cost;
    private LocalDate startDate;             // UML startDate
    private LocalDate endDate;               // UML endDate
    private String instructions;             // UML instructions

    @Override
    public String getId() {
        return treatmentId;
    }

    @Override
    public void setId(String id) {
        this.treatmentId = id;
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
        return "Treatment: " + description;
    }
}
