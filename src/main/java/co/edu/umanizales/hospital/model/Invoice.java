package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Invoice class demonstrating composition and the Billable interface
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice implements Registrable, Billable {
    private String invoiceId;
    private Patient patient;       // UML: Patient generates Invoices
    private String registrationDate;
    private LocalDate date;        // UML: date
    private double amount;         // UML: amount
    private String status;         // UML: status
    private String paymentMethod;  // UML: paymentMethod
    private String description;
    private List<Billable> items;  // Optional: computed items

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
        return amount;
    }

    @Override
    public String getBillingDescription() {
        return "Invoice #" + invoiceId + " - " + description;
    }

    /**
     * Helper: calculate amount based on items list
     */
    public void recomputeAmountFromItems() {
        if (items == null) {
            this.amount = 0.0;
            return;
        }
        this.amount = items.stream().mapToDouble(Billable::calculateCost).sum();
    }
}
