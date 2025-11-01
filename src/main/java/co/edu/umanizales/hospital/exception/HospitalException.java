package co.edu.umanizales.hospital.exception;

/**
 * Custom exception for Hospital Management System
 */
public class HospitalException extends RuntimeException {
    private String errorCode;
    private String details;

    public HospitalException(String message) {
        super(message);
        this.errorCode = "HOSPITAL_ERROR";
    }

    public HospitalException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public HospitalException(String message, String errorCode, String details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }

    public HospitalException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "HOSPITAL_ERROR";
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }
}
