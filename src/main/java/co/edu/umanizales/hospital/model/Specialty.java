package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Specialty class representing medical specialties
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialty implements Registrable {
    private String specialtyId;
    private String name;
    private String description;
    private String registrationDate;
    private double consultationCost;

    @Override
    public String getId() {
        return specialtyId;
    }

    @Override
    public void setId(String id) {
        this.specialtyId = id;
    }

    @Override
    public String getRegistrationDate() {
        return registrationDate;
    }
}
