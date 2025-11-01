# Hospital Management System API

## Descripción

Sistema REST API desarrollado en **Java 17** con **Spring Boot 3.5.6** y **Lombok** para la gestión operativa de un hospital. El sistema almacena toda la información en archivos **CSV** en lugar de una base de datos relacional.

## Características Principales

### Principios de Programación Orientada a Objetos (POO)

- **Encapsulamiento**: Protección de atributos con getters/setters usando Lombok
- **Herencia**: Clase abstracta `Person` heredada por `Patient`, `Doctor` y `Nurse`
- **Polimorfismo**: Métodos redefinidos en clases derivadas (ej: `calculateConsultationCost()`, `calculateNursingCost()`)
- **Interfaces**: Contratos comunes como `Registrable`, `Billable` y `Attendable`
- **Composición y Agregación**: Relaciones entre objetos (ej: `Appointment` contiene `Patient` y `Doctor`)

### Modelo de Datos (10+ Clases)

1. **Person** (Clase Abstracta)
   - Base para Paciente, Médico y Enfermero
   - Atributos: identificación, nombre, apellido, fecha nacimiento, contacto, etc.

2. **Patient** (Paciente)
   - Hereda de Person
   - Implementa Registrable
   - Atributos: ID paciente, contacto emergencia, estado

3. **Doctor** (Médico)
   - Hereda de Person
   - Implementa Registrable y Attendable
   - Atributos: licencia, especialidad, tarifa consulta
   - Método polimórfico: `calculateConsultationCost()`

4. **Nurse** (Enfermero)
   - Hereda de Person
   - Implementa Registrable y Attendable
   - Atributos: licencia, unidad asignada, tarifa horaria
   - Método polimórfico: `calculateNursingCost(hours)`

5. **Specialty** (Especialidad Médica)
   - Implementa Registrable
   - Atributos: nombre, descripción, costo consulta

6. **Appointment** (Cita)
   - Implementa Registrable y Billable
   - Composición: contiene referencias a Paciente y Médico
   - Atributos: fecha/hora, estado, razón, costo

7. **Medicine** (Medicamento)
   - Implementa Registrable y Billable
   - Atributos: nombre, ingrediente activo, precio, stock, fabricante

8. **Treatment** (Tratamiento)
   - Implementa Registrable y Billable
   - Agregación: agrupa medicamentos
   - Atributos: descripción, medicinas, estado, costo

9. **MedicalRecord** (Historia Clínica)
   - Implementa Registrable
   - Agregación: agrupa múltiples tratamientos
   - Atributos: historial médico, alergias, enfermedades crónicas

10. **Room** (Habitación)
    - Implementa Registrable y Attendable
    - Atributos: número, tipo, estado, capacidad, tarifa diaria
    - Método: `calculateStayCost(days)`

11. **Invoice** (Factura)
    - Implementa Registrable y Billable
    - Composición: agrupa múltiples items
    - Atributos: subtotal, impuesto, total, estado
    - Método polimórfico: `calculateTotal(taxRate)`

### Interfaces Implementadas

- **Registrable**: Define contrato para entidades registrables
  - `getId()`, `setId()`, `getRegistrationDate()`

- **Billable**: Define contrato para entidades facturables
  - `calculateCost()`, `getBillingDescription()`

- **Attendable**: Define contrato para entidades que pueden atender o ser atendidas
  - `getAttendanceType()`, `isAvailable()`

## Estructura del Proyecto

```
Hospital/
├── src/main/java/co/edu/umanizales/hospital/
│   ├── model/
│   │   ├── Person.java (Clase Abstracta)
│   │   ├── Patient.java
│   │   ├── Doctor.java
│   │   ├── Nurse.java
│   │   ├── Specialty.java
│   │   ├── Appointment.java
│   │   ├── Medicine.java
│   │   ├── Treatment.java
│   │   ├── MedicalRecord.java
│   │   ├── Room.java
│   │   ├── Invoice.java
│   │   ├── Registrable.java (Interface)
│   │   ├── Billable.java (Interface)
│   │   └── Attendable.java (Interface)
│   ├── service/
│   │   ├── CsvService.java
│   │   ├── PatientService.java
│   │   ├── DoctorService.java
│   │   ├── NurseService.java
│   │   ├── SpecialtyService.java
│   │   ├── AppointmentService.java
│   │   ├── MedicineService.java
│   │   ├── TreatmentService.java
│   │   ├── MedicalRecordService.java
│   │   ├── RoomService.java
│   │   └── InvoiceService.java
│   ├── controller/
│   │   ├── ApiController.java
│   │   ├── PatientController.java
│   │   ├── DoctorController.java
│   │   ├── NurseController.java
│   │   ├── SpecialtyController.java
│   │   ├── AppointmentController.java
│   │   ├── MedicineController.java
│   │   ├── TreatmentController.java
│   │   ├── MedicalRecordController.java
│   │   ├── RoomController.java
│   │   └── InvoiceController.java
│   └── HospitalApplication.java
├── src/main/resources/
│   ├── application.properties
│   └── proyecto.md
├── data/ (Directorio de archivos CSV - creado automáticamente)
│   ├── patients.csv
│   ├── doctors.csv
│   ├── nurses.csv
│   ├── specialties.csv
│   ├── appointments.csv
│   ├── medicines.csv
│   ├── treatments.csv
│   ├── medical_records.csv
│   ├── rooms.csv
│   └── invoices.csv
└── pom.xml
```

## Dependencias

- **Spring Boot**: 3.5.6
- **Lombok**: Para reducir código boilerplate
- **OpenCSV**: Para manejo de archivos CSV
- **JUnit 5**: Para testing
- **Mockito**: Para mocking en tests

## Instalación y Ejecución

### Requisitos
- Java 17 o superior
- Maven 3.6+

### Pasos

1. **Clonar o descargar el proyecto**
   ```bash
   cd Hospital
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean install
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

4. **Acceder a la API**
   - URL base: `http://localhost:8080/api`
   - Health check: `http://localhost:8080/api/health`
   - Info: `http://localhost:8080/api/info`

## Endpoints de la API

### Pacientes
- `POST /api/patients` - Crear paciente
- `GET /api/patients` - Obtener todos los pacientes
- `GET /api/patients/{patientId}` - Obtener paciente por ID
- `PUT /api/patients/{patientId}` - Actualizar paciente
- `DELETE /api/patients/{patientId}` - Eliminar paciente

### Médicos
- `POST /api/doctors` - Crear médico
- `GET /api/doctors` - Obtener todos los médicos
- `GET /api/doctors/{doctorId}` - Obtener médico por ID
- `PUT /api/doctors/{doctorId}` - Actualizar médico
- `DELETE /api/doctors/{doctorId}` - Eliminar médico
- `GET /api/doctors/specialty/{specialtyId}` - Obtener médicos por especialidad

### Enfermeros
- `POST /api/nurses` - Crear enfermero
- `GET /api/nurses` - Obtener todos los enfermeros
- `GET /api/nurses/{nurseId}` - Obtener enfermero por ID
- `PUT /api/nurses/{nurseId}` - Actualizar enfermero
- `DELETE /api/nurses/{nurseId}` - Eliminar enfermero
- `GET /api/nurses/unit/{unit}` - Obtener enfermeros por unidad

### Especialidades
- `POST /api/specialties` - Crear especialidad
- `GET /api/specialties` - Obtener todas las especialidades
- `GET /api/specialties/{specialtyId}` - Obtener especialidad por ID
- `PUT /api/specialties/{specialtyId}` - Actualizar especialidad
- `DELETE /api/specialties/{specialtyId}` - Eliminar especialidad

### Citas
- `POST /api/appointments` - Crear cita
- `GET /api/appointments` - Obtener todas las citas
- `GET /api/appointments/{appointmentId}` - Obtener cita por ID
- `PUT /api/appointments/{appointmentId}` - Actualizar cita
- `DELETE /api/appointments/{appointmentId}` - Eliminar cita
- `GET /api/appointments/patient/{patientId}` - Obtener citas por paciente
- `GET /api/appointments/doctor/{doctorId}` - Obtener citas por médico

### Medicamentos
- `POST /api/medicines` - Crear medicamento
- `GET /api/medicines` - Obtener todos los medicamentos
- `GET /api/medicines/{medicineId}` - Obtener medicamento por ID
- `PUT /api/medicines/{medicineId}` - Actualizar medicamento
- `DELETE /api/medicines/{medicineId}` - Eliminar medicamento
- `GET /api/medicines/manufacturer/{manufacturer}` - Obtener medicamentos por fabricante

### Tratamientos
- `POST /api/treatments` - Crear tratamiento
- `GET /api/treatments` - Obtener todos los tratamientos
- `GET /api/treatments/{treatmentId}` - Obtener tratamiento por ID
- `PUT /api/treatments/{treatmentId}` - Actualizar tratamiento
- `DELETE /api/treatments/{treatmentId}` - Eliminar tratamiento
- `GET /api/treatments/patient/{patientId}` - Obtener tratamientos por paciente

### Historias Clínicas
- `POST /api/medical-records` - Crear historia clínica
- `GET /api/medical-records` - Obtener todas las historias clínicas
- `GET /api/medical-records/{recordId}` - Obtener historia clínica por ID
- `GET /api/medical-records/patient/{patientId}` - Obtener historia clínica por paciente
- `PUT /api/medical-records/{recordId}` - Actualizar historia clínica
- `DELETE /api/medical-records/{recordId}` - Eliminar historia clínica

### Habitaciones
- `POST /api/rooms` - Crear habitación
- `GET /api/rooms` - Obtener todas las habitaciones
- `GET /api/rooms/{roomId}` - Obtener habitación por ID
- `PUT /api/rooms/{roomId}` - Actualizar habitación
- `DELETE /api/rooms/{roomId}` - Eliminar habitación
- `GET /api/rooms/available` - Obtener habitaciones disponibles
- `GET /api/rooms/type/{type}` - Obtener habitaciones por tipo

### Facturas
- `POST /api/invoices` - Crear factura
- `GET /api/invoices` - Obtener todas las facturas
- `GET /api/invoices/{invoiceId}` - Obtener factura por ID
- `PUT /api/invoices/{invoiceId}` - Actualizar factura
- `DELETE /api/invoices/{invoiceId}` - Eliminar factura
- `GET /api/invoices/patient/{patientId}` - Obtener facturas por paciente
- `GET /api/invoices/status/{status}` - Obtener facturas por estado

## Ejemplo de Uso con cURL

### Crear un Paciente
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "identification": "12345678",
    "firstName": "Juan",
    "lastName": "Pérez",
    "birthDate": "1990-05-15",
    "phone": "3001234567",
    "email": "juan@example.com",
    "address": "Calle 10 #20-30",
    "gender": "MALE",
    "bloodType": "O+",
    "emergencyContact": "María Pérez",
    "emergencyPhone": "3009876543",
    "status": "ACTIVE"
  }'
```

### Obtener Todos los Pacientes
```bash
curl http://localhost:8080/api/patients
```

### Actualizar un Paciente
```bash
curl -X PUT http://localhost:8080/api/patients/{patientId} \
  -H "Content-Type: application/json" \
  -d '{
    "identification": "12345678",
    "firstName": "Juan",
    "lastName": "Pérez",
    "status": "INACTIVE"
  }'
```

### Eliminar un Paciente
```bash
curl -X DELETE http://localhost:8080/api/patients/{patientId}
```

## Almacenamiento en CSV

Los datos se almacenan automáticamente en archivos CSV en el directorio `data/`:

- **patients.csv**: Información de pacientes
- **doctors.csv**: Información de médicos
- **nurses.csv**: Información de enfermeros
- **specialties.csv**: Especialidades médicas
- **appointments.csv**: Citas médicas
- **medicines.csv**: Medicamentos
- **treatments.csv**: Tratamientos
- **medical_records.csv**: Historias clínicas
- **rooms.csv**: Habitaciones del hospital
- **invoices.csv**: Facturas

## Características de Diseño OOP

### Encapsulamiento
- Uso de Lombok `@Data` para generar getters/setters automáticamente
- Atributos protegidos en la clase abstracta `Person`
- Control de acceso a través de métodos públicos

### Herencia
```java
public abstract class Person { ... }
public class Patient extends Person implements Registrable { ... }
public class Doctor extends Person implements Registrable, Attendable { ... }
public class Nurse extends Person implements Registrable, Attendable { ... }
```

### Polimorfismo
- Método abstracto `getRole()` implementado en cada subclase
- Métodos especializados: `calculateConsultationCost()`, `calculateNursingCost()`, `calculateStayCost()`
- Implementación de interfaces con comportamiento específico

### Interfaces
- `Registrable`: Para entidades que se registran en el sistema
- `Billable`: Para entidades que generan costos
- `Attendable`: Para entidades que participan en la atención

### Composición y Agregación
- `Appointment` contiene referencias a `Patient` y `Doctor`
- `Treatment` agrupa múltiples `Medicine`
- `MedicalRecord` agrupa múltiples `Treatment`
- `Invoice` agrupa múltiples items

## Manejo de Errores

La API implementa manejo robusto de errores:
- Respuestas HTTP apropiadas (200, 201, 404, 500)
- Logging detallado de errores
- Validación de datos de entrada

## Logging

El sistema implementa logging con SLF4J:
- Nivel DEBUG para operaciones del hospital
- Nivel INFO para operaciones generales
- Logs estructurados con timestamp

## Autor

Desarrollado como proyecto académico de Programación 3

## Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.
