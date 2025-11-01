package co.edu.umanizales.hospital.model;

/**
 * Interface that defines the contract for registrable entities in the hospital system
 */
public interface Registrable {
    String getId();
    void setId(String id);
    String getRegistrationDate();
}
