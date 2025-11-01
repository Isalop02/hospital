package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Invoice class demonstrating composition and the Billable interface
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice implements Registrable, Billable {
    private String invoiceId;
    private String patientId;
    private String registrationDate;
    private String invoiceDate;
    private double subtotal;
    private double tax;
    private double total;
    private InvoiceStatus status;
    private String description;
    private String itemIds;  // Comma-separated IDs of appointments, treatments, medicines, rooms

    public enum InvoiceStatus {
        PENDING, PAID, PARTIALLY_PAID, CANCELLED
    }

    @Override
    public String getId() {
        return invoiceId;
    }

    @Override
    public void setId(String id) {
        this.invoiceId = id;
    }

    @Override
    public String getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public double calculateCost() {
        return total;
    }

    @Override
    public String getBillingDescription() {
        return "Invoice #" + invoiceId + " - " + description;
    }

    /**
     * Polymorphic method: calculate total with tax
     */
    public void calculateTotal(double taxRate) {
        this.tax = subtotal * taxRate;
        this.total = subtotal + tax;
    }
}
