package co.edu.umanizales.hospital.controller;

import co.edu.umanizales.hospital.model.Patient;
import co.edu.umanizales.hospital.service.PatientService;
import co.edu.umanizales.hospital.util.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Patient Controller Tests")
class PatientControllerTest {

    private PatientController patientController;

    @Mock
    private PatientService patientServiceMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientController = new PatientController(patientServiceMock);
    }

    @Test
    @DisplayName("Should create patient and return 201 Created")
    void testCreatePatient() {
        // Arrange
        Patient patient = DataGenerator.generateSamplePatient();
        Patient createdPatient = DataGenerator.generateSamplePatient();
        createdPatient.setPatientId("patient-123");

        when(patientServiceMock.createPatient(patient))
            .thenReturn(createdPatient);

        // Act
        ResponseEntity<Patient> response = patientController.createPatient(patient);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("patient-123", response.getBody().getPatientId());
        verify(patientServiceMock, times(1)).createPatient(patient);
    }

    @Test
    @DisplayName("Should retrieve patient and return 200 OK")
    void testGetPatientById() {
        // Arrange
        String patientId = "patient-123";
        Patient patient = DataGenerator.generateSamplePatient();
        patient.setPatientId(patientId);

        when(patientServiceMock.getPatientById(patientId))
            .thenReturn(patient);

        // Act
        ResponseEntity<Patient> response = patientController.getPatientById(patientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(patientId, response.getBody().getPatientId());
    }

    @Test
    @DisplayName("Should return 404 when patient not found")
    void testGetPatientByIdNotFound() {
        // Arrange
        String patientId = "non-existent-id";
        when(patientServiceMock.getPatientById(patientId))
            .thenReturn(null);

        // Act
        ResponseEntity<Patient> response = patientController.getPatientById(patientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Should retrieve all patients and return 200 OK")
    void testGetAllPatients() {
        // Arrange
        Patient patient1 = DataGenerator.generateSamplePatient();
        patient1.setPatientId("patient-1");
        Patient patient2 = DataGenerator.generateSamplePatient();
        patient2.setPatientId("patient-2");

        List<Patient> patients = Arrays.asList(patient1, patient2);

        when(patientServiceMock.getAllPatients())
            .thenReturn(patients);

        // Act
        ResponseEntity<List<Patient>> response = patientController.getAllPatients();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @DisplayName("Should update patient and return 200 OK")
    void testUpdatePatient() {
        // Arrange
        String patientId = "patient-123";
        Patient patient = DataGenerator.generateSamplePatient();
        patient.setPatientId(patientId);

        when(patientServiceMock.updatePatient(patientId, patient))
            .thenReturn(patient);

        // Act
        ResponseEntity<Patient> response = patientController.updatePatient(patientId, patient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(patientId, response.getBody().getPatientId());
    }

    @Test
    @DisplayName("Should delete patient and return 204 No Content")
    void testDeletePatient() {
        // Arrange
        String patientId = "patient-123";
        when(patientServiceMock.deletePatient(patientId))
            .thenReturn(true);

        // Act
        ResponseEntity<Void> response = patientController.deletePatient(patientId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(patientServiceMock, times(1)).deletePatient(patientId);
    }

    @Test
    @DisplayName("Should return 404 when deleting non-existent patient")
    void testDeletePatientNotFound() {
        // Arrange
        String patientId = "non-existent-id";
        when(patientServiceMock.deletePatient(patientId))
            .thenReturn(false);

        // Act
        ResponseEntity<Void> response = patientController.deletePatient(patientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
