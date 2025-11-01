package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Abstract class representing a person in the hospital system
 * Demonstrates encapsulation and inheritance principles
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {
    protected String identification;
    protected String firstName;
    protected String lastName;
    protected LocalDate birthDate;
    protected String phone;
    protected String email;
    protected String address;
    protected Gender gender;
    protected String bloodType;

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    /**
     * Abstract method to get the person's role in the hospital
     */
    public abstract String getRole();

    /**
     * Method to get full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
