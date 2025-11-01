package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Medicine class representing medications in the hospital
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine implements Registrable, Billable {
    private String medicineId;
    private String name;
    private String activeIngredient;
    private String registrationDate;
    private double unitPrice;
    private int stockQuantity;
    private String manufacturer;
    private String expirationDate;

    @Override
    public String getId() {
        return medicineId;
    }

    @Override
    public void setId(String id) {
        this.medicineId = id;
    }

    @Override
    public String getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public double calculateCost() {
        return unitPrice;
    }

    @Override
    public String getBillingDescription() {
        return "Medicine: " + name + " - " + activeIngredient;
    }

    public double calculateTotalCost(int quantity) {
        return unitPrice * quantity;
    }
}
