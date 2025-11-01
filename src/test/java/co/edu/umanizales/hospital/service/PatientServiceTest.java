package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.Patient;
import co.edu.umanizales.hospital.util.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Patient Service Tests")
class PatientServiceTest {

    private PatientService patientService;

    @Mock
    private CsvService csvServiceMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientService(csvServiceMock);
    }

    @Test
    @DisplayName("Should create a patient successfully")
    void testCreatePatient() {
        // Arrange
        Patient patient = DataGenerator.generateSamplePatient();

        // Act
        Patient createdPatient = patientService.createPatient(patient);

        // Assert
        assertNotNull(createdPatient);
        assertNotNull(createdPatient.getPatientId());
        assertEquals(patient.getFirstName(), createdPatient.getFirstName());
        assertEquals(patient.getLastName(), createdPatient.getLastName());
        assertNotNull(createdPatient.getRegistrationDate());
    }

    @Test
    @DisplayName("Should retrieve patient by ID")
    void testGetPatientById() {
        // Arrange
        String patientId = "test-patient-123";
        String[] mockRecord = new String[]{
            patientId, "1234567890", "Carlos", "García", "1985-03-20",
            "3001234567", "carlos@email.com", "Calle 5", "MALE", "O+",
            "Ana García", "3009876543", "2025-10-30", "ACTIVE"
        };

        when(csvServiceMock.findRecordById("patients", patientId, 0))
            .thenReturn(mockRecord);

        // Act
        Patient patient = patientService.getPatientById(patientId);

        // Assert
        assertNotNull(patient);
        assertEquals(patientId, patient.getPatientId());
        assertEquals("Carlos", patient.getFirstName());
        verify(csvServiceMock, times(1)).findRecordById("patients", patientId, 0);
    }

    @Test
    @DisplayName("Should return null when patient not found")
    void testGetPatientByIdNotFound() {
        // Arrange
        String patientId = "non-existent-id";
        when(csvServiceMock.findRecordById("patients", patientId, 0))
            .thenReturn(null);

        // Act
        Patient patient = patientService.getPatientById(patientId);

        // Assert
        assertNull(patient);
    }

    @Test
    @DisplayName("Should delete patient successfully")
    void testDeletePatient() {
        // Arrange
        String patientId = "test-patient-123";
        when(csvServiceMock.deleteRecordById("patients", patientId, 0))
            .thenReturn(true);

        // Act
        boolean deleted = patientService.deletePatient(patientId);

        // Assert
        assertTrue(deleted);
        verify(csvServiceMock, times(1)).deleteRecordById("patients", patientId, 0);
    }

    @Test
    @DisplayName("Should return false when deleting non-existent patient")
    void testDeletePatientNotFound() {
        // Arrange
        String patientId = "non-existent-id";
        when(csvServiceMock.deleteRecordById("patients", patientId, 0))
            .thenReturn(false);

        // Act
        boolean deleted = patientService.deletePatient(patientId);

        // Assert
        assertFalse(deleted);
    }
}
