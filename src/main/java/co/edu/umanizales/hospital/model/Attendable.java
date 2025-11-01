package co.edu.umanizales.hospital.model;

/**
 * Interface that defines the contract for attendable entities (those who can attend or be attended)
 */
public interface Attendable {
    String getAttendanceType();
    boolean isAvailable();
}
