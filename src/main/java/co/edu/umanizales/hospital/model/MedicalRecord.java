package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MedicalRecord class demonstrating aggregation
 * Aggregates multiple treatments for a patient
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord implements Registrable {
    private String recordId;
    private String patientId;
    private String registrationDate;
    private String medicalHistory;
    private String allergies;
    private String chronicDiseases;
    private String treatmentIds;  // Comma-separated treatment IDs
    private String lastUpdateDate;

    @Override
    public String getId() {
        return recordId;
    }

    @Override
    public void setId(String id) {
        this.recordId = id;
    }

    @Override
    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Method to add treatment to medical record
     */
    public void addTreatment(String treatmentId) {
        if (treatmentIds == null || treatmentIds.isEmpty()) {
            treatmentIds = treatmentId;
        } else {
            treatmentIds += "," + treatmentId;
        }
    }
}
