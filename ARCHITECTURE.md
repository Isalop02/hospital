# Arquitectura del Hospital Management System

## Descripción General

El Hospital Management System es una API REST desarrollada con Spring Boot que implementa principios sólidos de Programación Orientada a Objetos (POO). El sistema utiliza archivos CSV para la persistencia de datos en lugar de una base de datos relacional.

## Principios de Diseño OOP Implementados

### 1. Encapsulamiento

El encapsulamiento se implementa mediante:

- **Lombok `@Data`**: Genera automáticamente getters, setters, equals(), hashCode() y toString()
- **Atributos Protegidos**: En la clase abstracta `Person`, los atributos son protegidos para permitir acceso a las subclases
- **Métodos Públicos**: Control de acceso a través de métodos públicos

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {
    protected String identification;
    protected String firstName;
    // ... más atributos
}
```

### 2. Herencia

La herencia se implementa con una jerarquía clara:

```
Person (Clase Abstracta)
├── Patient (Paciente)
├── Doctor (Médico)
└── Nurse (Enfermero)
```

**Características:**
- `Person` es la clase base abstracta que define atributos comunes
- Cada subclase hereda los atributos de `Person`
- Cada subclase implementa el método abstracto `getRole()`
- Las subclases pueden tener atributos y métodos específicos

```java
public abstract class Person {
    public abstract String getRole();
}

public class Patient extends Person {
    @Override
    public String getRole() {
        return "PATIENT";
    }
}
```

### 3. Polimorfismo

El polimorfismo se implementa de dos formas:

#### a) Polimorfismo de Métodos (Sobrescritura)

```java
// Método abstracto en la clase base
public abstract String getRole();

// Implementaciones específicas en subclases
public class Doctor extends Person {
    @Override
    public String getRole() {
        return "DOCTOR";
    }
}

public class Nurse extends Person {
    @Override
    public String getRole() {
        return "NURSE";
    }
}
```

#### b) Polimorfismo de Métodos Especializados

```java
// Doctor
public double calculateConsultationCost() {
    return consultationFee;
}

// Nurse
public double calculateNursingCost(int hours) {
    return hourlyRate * hours;
}

// Room
public double calculateStayCost(int days) {
    return dailyRate * days;
}

// Invoice
public void calculateTotal(double taxRate) {
    this.tax = subtotal * taxRate;
    this.total = subtotal + tax;
}
```

### 4. Interfaces

Se implementan tres interfaces principales que definen contratos:

#### a) Registrable
```java
public interface Registrable {
    String getId();
    void setId(String id);
    String getRegistrationDate();
}
```
Implementada por: `Patient`, `Doctor`, `Nurse`, `Specialty`, `Appointment`, `Medicine`, `Treatment`, `MedicalRecord`, `Room`, `Invoice`

#### b) Billable
```java
public interface Billable {
    double calculateCost();
    String getBillingDescription();
}
```
Implementada por: `Appointment`, `Medicine`, `Treatment`, `Invoice`

#### c) Attendable
```java
public interface Attendable {
    String getAttendanceType();
    boolean isAvailable();
}
```
Implementada por: `Doctor`, `Nurse`, `Room`

### 5. Composición y Agregación

#### a) Composición (Relación Fuerte)

`Appointment` contiene referencias a `Patient` y `Doctor`:
```java
public class Appointment implements Registrable, Billable {
    private String patientId;      // Referencia a Patient
    private String doctorId;       // Referencia a Doctor
    // ...
}
```

#### b) Agregación (Relación Débil)

`Treatment` agrupa múltiples `Medicine`:
```java
public class Treatment implements Registrable, Billable {
    private String medicineIds;    // IDs separados por comas
    // ...
}
```

`MedicalRecord` agrupa múltiples `Treatment`:
```java
public class MedicalRecord implements Registrable {
    private String treatmentIds;   // IDs separados por comas
    // ...
}
```

## Arquitectura en Capas

### Capa de Presentación (Controllers)

Los controladores REST manejan las solicitudes HTTP:

```
PatientController
├── POST /api/patients
├── GET /api/patients
├── GET /api/patients/{id}
├── PUT /api/patients/{id}
└── DELETE /api/patients/{id}
```

**Características:**
- Manejo de solicitudes HTTP
- Validación de entrada
- Manejo de excepciones
- Respuestas HTTP apropiadas

### Capa de Negocio (Services)

Los servicios implementan la lógica de negocio:

```java
@Service
public class PatientService {
    private final CsvService csvService;
    
    public Patient createPatient(Patient patient) { ... }
    public Patient getPatientById(String patientId) { ... }
    public List<Patient> getAllPatients() { ... }
    public Patient updatePatient(String id, Patient patient) { ... }
    public boolean deletePatient(String patientId) { ... }
}
```

**Características:**
- Lógica de negocio centralizada
- Conversión entre objetos y arrays CSV
- Manejo de IDs únicos (UUID)
- Gestión de fechas

### Capa de Persistencia (CSV Service)

El `CsvService` maneja la lectura/escritura de archivos CSV:

```java
@Service
public class CsvService {
    public List<String[]> readCsv(String filename) { ... }
    public void writeCsv(String filename, List<String[]> records) { ... }
    public void appendToCsv(String filename, String[] record) { ... }
    public String[] findRecordById(String filename, String id, int idColumnIndex) { ... }
    public boolean deleteRecordById(String filename, String id, int idColumnIndex) { ... }
    public boolean updateRecordById(String filename, String id, String[] newRecord, int idColumnIndex) { ... }
}
```

### Capa de Modelo (Domain Models)

Las clases del modelo representan entidades del dominio:

```
Interfaces
├── Registrable
├── Billable
└── Attendable

Clases Abstractas
└── Person

Clases Concretas
├── Patient
├── Doctor
├── Nurse
├── Specialty
├── Appointment
├── Medicine
├── Treatment
├── MedicalRecord
├── Room
└── Invoice
```

## Flujo de Datos

### Crear un Paciente

```
1. Cliente HTTP
   ↓
2. PatientController.createPatient()
   ↓
3. PatientService.createPatient()
   ├─ Genera UUID para patientId
   ├─ Establece fecha de registro
   ├─ Convierte Patient a array CSV
   ↓
4. CsvService.appendToCsv()
   ├─ Lee archivo patients.csv
   ├─ Agrega nuevo registro
   ├─ Escribe archivo actualizado
   ↓
5. Retorna Patient creado al cliente
```

### Obtener un Paciente

```
1. Cliente HTTP
   ↓
2. PatientController.getPatientById()
   ↓
3. PatientService.getPatientById()
   ↓
4. CsvService.findRecordById()
   ├─ Lee archivo patients.csv
   ├─ Busca registro por ID
   ↓
5. Convierte array CSV a Patient
   ↓
6. Retorna Patient al cliente
```

## Estructura de Archivos CSV

Cada entidad tiene su propio archivo CSV con estructura específica:

### patients.csv
```
patientId,identification,firstName,lastName,birthDate,phone,email,address,gender,bloodType,emergencyContact,emergencyPhone,registrationDate,status
```

### doctors.csv
```
doctorId,identification,firstName,lastName,birthDate,phone,email,address,gender,bloodType,licenseNumber,specialtyId,registrationDate,status,consultationFee
```

### appointments.csv
```
appointmentId,patientId,doctorId,appointmentDateTime,registrationDate,status,reason,notes,cost
```

## Patrones de Diseño Utilizados

### 1. Service Layer Pattern
Separa la lógica de negocio de la presentación

### 2. Data Access Object (DAO) Pattern
`CsvService` actúa como DAO para acceso a datos

### 3. Factory Pattern
`DataGenerator` crea instancias de prueba

### 4. Singleton Pattern
`CsvService` es un singleton (Spring Bean)

### 5. Template Method Pattern
Métodos comunes en servicios (CRUD)

## Manejo de Excepciones

### Jerarquía de Excepciones

```
Exception
├── HospitalException (Personalizada)
└── IllegalArgumentException
```

### Global Exception Handler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HospitalException.class)
    @ExceptionHandler(IllegalArgumentException.class)
    @ExceptionHandler(Exception.class)
}
```

## Testing

### Niveles de Testing

1. **Unit Tests**: Pruebas de servicios y controladores
2. **Integration Tests**: Pruebas de flujos completos
3. **Mock Testing**: Uso de Mockito para aislar componentes

### Ejemplo de Test

```java
@Test
void testCreatePatient() {
    Patient patient = DataGenerator.generateSamplePatient();
    Patient created = patientService.createPatient(patient);
    
    assertNotNull(created.getPatientId());
    assertEquals(patient.getFirstName(), created.getFirstName());
}
```

## Ventajas de la Arquitectura

1. **Separación de Responsabilidades**: Cada capa tiene una responsabilidad clara
2. **Reutilización de Código**: Herencia y composición reducen duplicación
3. **Mantenibilidad**: Código organizado y fácil de entender
4. **Escalabilidad**: Fácil agregar nuevas entidades
5. **Testabilidad**: Componentes desacoplados facilitan testing
6. **Flexibilidad**: Interfaces permiten múltiples implementaciones

## Mejoras Futuras

1. Migrar a base de datos relacional (PostgreSQL, MySQL)
2. Implementar autenticación y autorización (JWT)
3. Agregar validación de datos con Bean Validation
4. Implementar paginación en listados
5. Agregar filtros avanzados de búsqueda
6. Implementar auditoría de cambios
7. Agregar documentación Swagger/OpenAPI
8. Implementar caché (Redis)
9. Agregar transacciones distribuidas
10. Implementar eventos de dominio

## Conclusión

El Hospital Management System demuestra la aplicación práctica de principios SOLID y patrones de diseño en una aplicación real. La arquitectura es escalable, mantenible y fácil de entender, proporcionando una base sólida para futuras mejoras.
