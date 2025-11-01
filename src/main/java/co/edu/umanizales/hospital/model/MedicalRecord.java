package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * MedicalRecord class demonstrating aggregation
 * Aggregates multiple treatments for a patient
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord implements Registrable {
    private String recordId;
    private Patient patient;          // UML: belongs to a Patient
    private Doctor doctor;            // UML: created by a Doctor
    private String registrationDate;
    private LocalDate date;           // UML: record date
    private String diagnosis;         // UML: diagnosis
    private String treatment;         // UML: treatment summary
    private String notes;             // UML: notes
    private List<Medicine> medicines; // UML: 1..* medicines contained

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
}
