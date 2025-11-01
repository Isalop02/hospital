package co.edu.umanizales.hospital.util;

import co.edu.umanizales.hospital.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for generating sample data for testing
 */
public class DataGenerator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Generate a sample patient
     */
    public static Patient generateSamplePatient() {
        Patient patient = new Patient();
        patient.setIdentification("1234567890");
        patient.setFirstName("Carlos");
        patient.setLastName("García");
        patient.setBirthDate(LocalDate.of(1985, 3, 20));
        patient.setPhone("3001234567");
        patient.setEmail("carlos.garcia@email.com");
        patient.setAddress("Calle 5 #10-20, Apartamento 301");
        patient.setGender(Person.Gender.MALE);
        patient.setBloodType("O+");
        patient.setEmergencyContact("Ana García");
        patient.setEmergencyPhone("3009876543");
        patient.setStatus(Patient.PatientStatus.ACTIVE);
        patient.setMedicalHistory("Sin antecedentes relevantes");
        patient.setAllergies("Ninguna");
        patient.setCurrentMedication("Ninguna");
        return patient;
    }

    /**
     * Generate a sample specialty
     */
    public static Specialty generateSampleSpecialty() {
        Specialty specialty = new Specialty();
        specialty.setName("Cardiología");
        specialty.setDescription("Especialidad dedicada al diagnóstico y tratamiento de enfermedades del corazón");
        specialty.setConsultationCost(150000.00);
        return specialty;
    }

    /**
     * Generate a sample doctor
     */
    public static Doctor generateSampleDoctor() {
        Doctor doctor = new Doctor();
        doctor.setIdentification("9876543210");
        doctor.setFirstName("Dr. Juan");
        doctor.setLastName("López");
        doctor.setBirthDate(LocalDate.of(1975, 7, 10));
        doctor.setPhone("3005555555");
        doctor.setEmail("juan.lopez@hospital.com");
        doctor.setAddress("Calle 20 #30-40");
        doctor.setGender(Person.Gender.MALE);
        doctor.setBloodType("AB+");
        doctor.setLicenseNumber("MED-2020-001");
        doctor.setSpecialties(java.util.List.of(generateSampleSpecialty()));
        doctor.setStatus(Doctor.DoctorStatus.ACTIVE);
        doctor.setConsultationFee(150000.00);
        return doctor;
    }

    /**
     * Generate a sample nurse
     */
    public static Nurse generateSampleNurse() {
        Nurse nurse = new Nurse();
        nurse.setIdentification("5555555555");
        nurse.setFirstName("María");
        nurse.setLastName("Rodríguez");
        nurse.setBirthDate(LocalDate.of(1992, 11, 15));
        nurse.setPhone("3007777777");
        nurse.setEmail("maria.rodriguez@hospital.com");
        nurse.setAddress("Calle 15 #25-35");
        nurse.setGender(Person.Gender.FEMALE);
        nurse.setBloodType("A+");
        nurse.setLicenseNumber("ENF-2021-005");
        nurse.setStatus(Nurse.NurseStatus.ACTIVE);
        nurse.setAssignedUnit("Cardiología");
        nurse.setHourlyRate(25000.00);
        nurse.setSpecializations(java.util.List.of(generateSampleSpecialty()));
        return nurse;
    }

    /**
     * Generate a sample appointment
     */
    public static Appointment generateSampleAppointment(Patient patient, Doctor doctor) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDateTime(LocalDateTime.of(2025, 11, 15, 10, 30));
        appointment.setStatus(Appointment.STATUS_SCHEDULED);
        appointment.setReason("Consulta de rutina - Dolor en el pecho");
        appointment.setNotes("Paciente refiere dolor leve en el pecho desde hace 3 días");
        appointment.setCost(150000.00);
        return appointment;
    }

    /**
     * Generate a sample medicine
     */
    public static Medicine generateSampleMedicine() {
        Medicine medicine = new Medicine();
        medicine.setName("Aspirina");
        medicine.setActiveIngredient("Ácido acetilsalicílico");
        medicine.setUnitPrice(5000.00);
        medicine.setStockQuantity(500);
        medicine.setManufacturer("Bayer");
        medicine.setExpirationDate("2026-12-31");
        return medicine;
    }

    /**
     * Generate a sample treatment
     */
    public static Treatment generateSampleTreatment(Patient patient, Doctor doctor) {
        Treatment treatment = new Treatment();
        treatment.setPatient(patient);
        treatment.setDoctor(doctor);
        treatment.setDescription("Tratamiento para hipertensión arterial");
        treatment.setMedicines(java.util.List.of(generateSampleMedicine()));
        treatment.setStatus("ACTIVE");
        treatment.setCost(500000.00);
        treatment.setStartDate(LocalDate.of(2025, 10, 30));
        treatment.setEndDate(LocalDate.of(2025, 12, 30));
        treatment.setInstructions("Tomar medicamentos según prescripción y control semanal");
        return treatment;
    }

    /**
     * Generate a sample medical record
     */
    public static MedicalRecord generateSampleMedicalRecord(Patient patient, Doctor doctor) {
        MedicalRecord record = new MedicalRecord();
        record.setPatient(patient);
        record.setDoctor(doctor);
        record.setDate(LocalDate.now());
        record.setDiagnosis("Hipertensión arterial");
        record.setTreatment("Cambios en estilo de vida y medicación");
        record.setNotes("Controlar presión diariamente");
        record.setMedicines(java.util.List.of(generateSampleMedicine()));
        return record;
    }

    /**
     * Generate a sample room
     */
    public static Room generateSampleRoom() {
        Room room = new Room();
        room.setRoomNumber("301");
        room.setRoomType(Room.RoomType.PRIVATE);
        room.setStatus(Room.RoomStatus.AVAILABLE);
        room.setCurrentPatient(null);
        room.setCapacity(1);
        room.setDailyRate(300000.00);
        return room;
    }

    /**
     * Generate a sample invoice
     */
    public static Invoice generateSampleInvoice(Patient patient) {
        Invoice invoice = new Invoice();
        invoice.setPatient(patient);
        invoice.setDate(LocalDate.now());
        invoice.setAmount(773500.00);
        invoice.setStatus("PENDING");
        invoice.setPaymentMethod("CREDIT_CARD");
        invoice.setDescription("Factura por consulta, tratamiento y medicamentos");
        invoice.setItems(new java.util.ArrayList<>());
        return invoice;
    }
}
