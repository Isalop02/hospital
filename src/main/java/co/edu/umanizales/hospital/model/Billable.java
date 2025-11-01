package co.edu.umanizales.hospital.model;

/**
 * Interface that defines the contract for billable entities
 */
public interface Billable {
    double calculateCost();
    String getBillingDescription();
}
