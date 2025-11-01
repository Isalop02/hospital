package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Treatment class demonstrating composition and aggregation
 * Contains references to medicines and procedures
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Treatment implements Registrable, Billable {
    private String treatmentId;
    private String patientId;
    private String doctorId;
    private String registrationDate;
    private String description;
    private String medicineIds;  // Comma-separated medicine IDs
    private TreatmentStatus status;
    private double cost;
    private String startDate;
    private String endDate;

    public enum TreatmentStatus {
        ACTIVE, COMPLETED, SUSPENDED, CANCELLED
    }

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
