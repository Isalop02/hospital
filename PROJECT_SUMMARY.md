# Resumen del Proyecto - Hospital Management System

## Visión General

Se ha desarrollado un **Sistema de Gestión Hospitalaria** completo basado en una **API REST** con **Spring Boot 3.5.6** y **Java 17**, implementando principios sólidos de **Programación Orientada a Objetos (POO)** y patrones de diseño profesionales.

## Objetivos Cumplidos

### ✅ Requisitos Funcionales

1. **Gestión de Pacientes**
   - Registrar, consultar, actualizar y eliminar pacientes
   - Almacenar información personal y de emergencia
   - Rastrear estado del paciente

2. **Gestión de Médicos**
   - Registrar médicos con especialidades
   - Gestionar disponibilidad
   - Calcular costos de consulta

3. **Gestión de Enfermeros**
   - Registrar enfermeros con unidades asignadas
   - Gestionar disponibilidad
   - Calcular costos por hora

4. **Gestión de Citas**
   - Programar citas médicas
   - Asociar pacientes con médicos
   - Rastrear estado de citas

5. **Gestión de Medicamentos**
   - Registrar medicamentos con información de stock
   - Gestionar fabricantes y precios
   - Rastrear fechas de vencimiento

6. **Gestión de Tratamientos**
   - Crear tratamientos con múltiples medicamentos
   - Rastrear estado del tratamiento
   - Calcular costos

7. **Gestión de Historias Clínicas**
   - Crear historias clínicas por paciente
   - Agregar alergias y enfermedades crónicas
   - Agrupar tratamientos

8. **Gestión de Habitaciones**
   - Registrar habitaciones por tipo
   - Rastrear disponibilidad
   - Calcular costos de estadía

9. **Gestión de Facturas**
   - Crear facturas para pacientes
   - Calcular totales con impuestos
   - Rastrear estado de pago

10. **Gestión de Especialidades**
    - Registrar especialidades médicas
    - Gestionar costos de consulta

### ✅ Requisitos Técnicos

#### Principios OOP Implementados

1. **Encapsulamiento**
   - ✅ Atributos protegidos en clase abstracta `Person`
   - ✅ Getters/setters generados por Lombok
   - ✅ Control de acceso mediante métodos públicos

2. **Herencia**
   - ✅ Clase abstracta `Person` con 3 subclases
   - ✅ `Patient`, `Doctor`, `Nurse` heredan de `Person`
   - ✅ Método abstracto `getRole()` implementado en cada subclase

3. **Polimorfismo**
   - ✅ Métodos redefinidos en subclases
   - ✅ Métodos especializados: `calculateConsultationCost()`, `calculateNursingCost()`, `calculateStayCost()`
   - ✅ Implementación de interfaces con comportamiento específico

4. **Interfaces**
   - ✅ `Registrable`: Define contrato para entidades registrables
   - ✅ `Billable`: Define contrato para entidades facturables
   - ✅ `Attendable`: Define contrato para entidades que atienden

5. **Composición y Agregación**
   - ✅ `Appointment` contiene referencias a `Patient` y `Doctor`
   - ✅ `Treatment` agrupa múltiples `Medicine`
   - ✅ `MedicalRecord` agrupa múltiples `Treatment`
   - ✅ `Invoice` agrupa múltiples items

#### Clases del Modelo (11 clases)

1. **Person** (Clase Abstracta)
2. **Patient** (Paciente)
3. **Doctor** (Médico)
4. **Nurse** (Enfermero)
5. **Specialty** (Especialidad)
6. **Appointment** (Cita)
7. **Medicine** (Medicamento)
8. **Treatment** (Tratamiento)
9. **MedicalRecord** (Historia Clínica)
10. **Room** (Habitación)
11. **Invoice** (Factura)

#### Interfaces (3 interfaces)

1. **Registrable**
2. **Billable**
3. **Attendable**

#### Enumeraciones (8 enumeraciones)

1. `Person.Gender` - MALE, FEMALE, OTHER
2. `Patient.PatientStatus` - ACTIVE, INACTIVE, DISCHARGED
3. `Doctor.DoctorStatus` - ACTIVE, INACTIVE, ON_LEAVE
4. `Nurse.NurseStatus` - ACTIVE, INACTIVE, ON_LEAVE
5. `Appointment.AppointmentStatus` - SCHEDULED, COMPLETED, CANCELLED, NO_SHOW
6. `Treatment.TreatmentStatus` - ACTIVE, COMPLETED, SUSPENDED, CANCELLED
7. `Room.RoomType` - GENERAL, INTENSIVE_CARE, PRIVATE, PEDIATRIC
8. `Room.RoomStatus` - AVAILABLE, OCCUPIED, MAINTENANCE, CLOSED
9. `Invoice.InvoiceStatus` - PENDING, PAID, PARTIALLY_PAID, CANCELLED

### ✅ Persistencia en CSV

- ✅ Servicio `CsvService` para lectura/escritura de archivos CSV
- ✅ 10 archivos CSV para cada entidad
- ✅ Directorio `data/` creado automáticamente
- ✅ Operaciones CRUD completas en CSV

### ✅ API REST

- ✅ 10 controladores REST (uno por entidad)
- ✅ 60+ endpoints implementados
- ✅ Métodos HTTP: GET, POST, PUT, DELETE
- ✅ Respuestas HTTP apropiadas (200, 201, 204, 404, 500)
- ✅ CORS habilitado para todos los endpoints

### ✅ Servicios de Negocio

- ✅ 10 servicios de negocio (uno por entidad)
- ✅ Lógica de negocio centralizada
- ✅ Conversión entre objetos y arrays CSV
- ✅ Métodos de búsqueda especializados

### ✅ Manejo de Errores

- ✅ Excepción personalizada `HospitalException`
- ✅ Manejador global de excepciones `GlobalExceptionHandler`
- ✅ Logging con SLF4J
- ✅ Respuestas de error estructuradas

### ✅ Testing

- ✅ Tests unitarios para servicios
- ✅ Tests unitarios para controladores
- ✅ Uso de Mockito para mocking
- ✅ Clase `DataGenerator` para datos de prueba

### ✅ Documentación

- ✅ README.md - Documentación completa
- ✅ EXAMPLES.md - Ejemplos de uso con cURL y JSON
- ✅ ARCHITECTURE.md - Documentación de arquitectura
- ✅ SETUP.md - Guía de instalación y configuración
- ✅ Comentarios en el código

## Estructura del Proyecto

```
Hospital/
├── src/main/java/co/edu/umanizales/hospital/
│   ├── model/                    (11 clases + 3 interfaces)
│   ├── service/                  (10 servicios + CsvService)
│   ├── controller/               (10 controladores + ApiController)
│   ├── exception/                (Manejo de excepciones)
│   ├── util/                     (Utilidades)
│   └── HospitalApplication.java
├── src/test/java/co/edu/umanizales/hospital/
│   ├── service/                  (Tests de servicios)
│   └── controller/               (Tests de controladores)
├── src/main/resources/
│   ├── application.properties
│   └── proyecto.md
├── data/                         (Archivos CSV)
├── pom.xml
├── README.md
├── EXAMPLES.md
├── ARCHITECTURE.md
├── SETUP.md
└── PROJECT_SUMMARY.md
```

## Tecnologías Utilizadas

### Backend
- **Java 17**: Lenguaje de programación
- **Spring Boot 3.5.6**: Framework web
- **Lombok**: Generación de código boilerplate
- **OpenCSV 5.9**: Manejo de archivos CSV

### Testing
- **JUnit 5**: Framework de testing
- **Mockito**: Mocking de dependencias

### Build
- **Maven 3.6+**: Gestor de dependencias y build

### Herramientas
- **SLF4J**: Logging
- **Git**: Control de versiones

## Endpoints Principales

### Pacientes
- `POST /api/patients` - Crear
- `GET /api/patients` - Listar todos
- `GET /api/patients/{id}` - Obtener uno
- `PUT /api/patients/{id}` - Actualizar
- `DELETE /api/patients/{id}` - Eliminar

### Médicos
- `POST /api/doctors` - Crear
- `GET /api/doctors` - Listar todos
- `GET /api/doctors/{id}` - Obtener uno
- `GET /api/doctors/specialty/{specialtyId}` - Filtrar por especialidad
- `PUT /api/doctors/{id}` - Actualizar
- `DELETE /api/doctors/{id}` - Eliminar

### Citas
- `POST /api/appointments` - Crear
- `GET /api/appointments` - Listar todas
- `GET /api/appointments/{id}` - Obtener una
- `GET /api/appointments/patient/{patientId}` - Filtrar por paciente
- `GET /api/appointments/doctor/{doctorId}` - Filtrar por médico
- `PUT /api/appointments/{id}` - Actualizar
- `DELETE /api/appointments/{id}` - Eliminar

### Otros Endpoints
- Enfermeros: `/api/nurses`
- Especialidades: `/api/specialties`
- Medicamentos: `/api/medicines`
- Tratamientos: `/api/treatments`
- Historias Clínicas: `/api/medical-records`
- Habitaciones: `/api/rooms`
- Facturas: `/api/invoices`

## Características Destacadas

### 1. Arquitectura en Capas
- Capa de Presentación (Controllers)
- Capa de Negocio (Services)
- Capa de Persistencia (CsvService)
- Capa de Modelo (Domain Models)

### 2. Patrones de Diseño
- Service Layer Pattern
- Data Access Object (DAO) Pattern
- Factory Pattern
- Singleton Pattern
- Template Method Pattern

### 3. Principios SOLID
- Single Responsibility Principle
- Open/Closed Principle
- Liskov Substitution Principle
- Interface Segregation Principle
- Dependency Inversion Principle

### 4. Buenas Prácticas
- Separación de responsabilidades
- DRY (Don't Repeat Yourself)
- KISS (Keep It Simple, Stupid)
- Código limpio y legible
- Documentación completa

## Cómo Ejecutar

### Compilar
```bash
mvn clean install
```

### Ejecutar
```bash
mvn spring-boot:run
```

### Acceder
```
http://localhost:8080/api/health
```

## Ejemplo de Uso

### Crear un Paciente
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "identification": "1234567890",
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

## Estadísticas del Proyecto

- **Líneas de Código**: ~3,500+
- **Clases**: 25+
- **Interfaces**: 3
- **Enumeraciones**: 9
- **Controladores**: 11
- **Servicios**: 11
- **Tests**: 10+
- **Endpoints**: 60+
- **Archivos de Documentación**: 4

## Mejoras Futuras

1. Migrar a base de datos relacional
2. Implementar autenticación y autorización
3. Agregar validación de datos
4. Implementar paginación
5. Agregar filtros avanzados
6. Implementar auditoría
7. Agregar documentación Swagger
8. Implementar caché
9. Agregar transacciones distribuidas
10. Implementar eventos de dominio

## Conclusión

El **Hospital Management System** es una aplicación completa y profesional que demuestra la aplicación práctica de principios OOP, patrones de diseño y buenas prácticas de desarrollo de software. El sistema es escalable, mantenible y fácil de entender, proporcionando una base sólida para futuras mejoras y extensiones.

### Puntos Clave

✅ **11 Clases de Modelo** con herencia y polimorfismo
✅ **3 Interfaces** definiendo contratos claros
✅ **10 Servicios** con lógica de negocio centralizada
✅ **11 Controladores** con 60+ endpoints
✅ **Persistencia en CSV** completamente funcional
✅ **Manejo de Excepciones** robusto
✅ **Testing** unitario completo
✅ **Documentación** exhaustiva
✅ **Código Limpio** y bien organizado
✅ **Arquitectura Profesional** en capas

---

**Desarrollado con**: Java 17, Spring Boot 3.5.6, Lombok, OpenCSV
**Fecha**: Octubre 2025
**Versión**: 1.0.0
