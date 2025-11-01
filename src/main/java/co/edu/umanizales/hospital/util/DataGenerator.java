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
    public static Doctor generateSampleDoctor(String specialtyId) {
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
        doctor.setSpecialtyId(specialtyId);
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
        return nurse;
    }

    /**
     * Generate a sample appointment
     */
    public static Appointment generateSampleAppointment(String patientId, String doctorId) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDateTime(LocalDateTime.of(2025, 11, 15, 10, 30));
        appointment.setStatus(Appointment.AppointmentStatus.SCHEDULED);
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
    public static Treatment generateSampleTreatment(String patientId, String doctorId) {
        Treatment treatment = new Treatment();
        treatment.setPatientId(patientId);
        treatment.setDoctorId(doctorId);
        treatment.setDescription("Tratamiento para hipertensión arterial");
        treatment.setMedicineIds("med-001,med-002,med-003");
        treatment.setStatus(Treatment.TreatmentStatus.ACTIVE);
        treatment.setCost(500000.00);
        treatment.setStartDate("2025-10-30");
        treatment.setEndDate("2025-12-30");
        return treatment;
    }

    /**
     * Generate a sample medical record
     */
    public static MedicalRecord generateSampleMedicalRecord(String patientId) {
        MedicalRecord record = new MedicalRecord();
        record.setPatientId(patientId);
        record.setMedicalHistory("Paciente con antecedentes de hipertensión desde hace 5 años");
        record.setAllergies("Penicilina, Ibuprofeno");
        record.setChronicDiseases("Hipertensión arterial, Diabetes tipo 2");
        record.setTreatmentIds("treat-001,treat-002");
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
        room.setCurrentPatientId(null);
        room.setCapacity(1);
        room.setDailyRate(300000.00);
        return room;
    }

    /**
     * Generate a sample invoice
     */
    public static Invoice generateSampleInvoice(String patientId) {
        Invoice invoice = new Invoice();
        invoice.setPatientId(patientId);
        invoice.setSubtotal(650000.00);
        invoice.setTax(123500.00);
        invoice.setTotal(773500.00);
        invoice.setStatus(Invoice.InvoiceStatus.PENDING);
        invoice.setDescription("Factura por consulta, tratamiento y medicamentos");
        invoice.setItemIds("apt-001,treat-001,med-001");
        return invoice;
    }
}
