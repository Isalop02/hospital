# Ejemplos de Uso del Hospital Management System API

## Ejemplos JSON para Requests

### 1. Crear un Paciente

**Endpoint:** `POST /api/patients`

```json
{
  "identification": "1234567890",
  "firstName": "Carlos",
  "lastName": "García",
  "birthDate": "1985-03-20",
  "phone": "3001234567",
  "email": "carlos.garcia@email.com",
  "address": "Calle 5 #10-20, Apartamento 301",
  "gender": "MALE",
  "bloodType": "O+",
  "emergencyContact": "Ana García",
  "emergencyPhone": "3009876543",
  "status": "ACTIVE"
}
```

**Respuesta (201 Created):**
```json
{
  "patientId": "550e8400-e29b-41d4-a716-446655440000",
  "identification": "1234567890",
  "firstName": "Carlos",
  "lastName": "García",
  "birthDate": "1985-03-20",
  "phone": "3001234567",
  "email": "carlos.garcia@email.com",
  "address": "Calle 5 #10-20, Apartamento 301",
  "gender": "MALE",
  "bloodType": "O+",
  "emergencyContact": "Ana García",
  "emergencyPhone": "3009876543",
  "registrationDate": "2025-10-30",
  "status": "ACTIVE"
}
```

### 2. Crear una Especialidad

**Endpoint:** `POST /api/specialties`

```json
{
  "name": "Cardiología",
  "description": "Especialidad dedicada al diagnóstico y tratamiento de enfermedades del corazón",
  "consultationCost": 150000.00
}
```

**Respuesta (201 Created):**
```json
{
  "specialtyId": "660e8400-e29b-41d4-a716-446655440001",
  "name": "Cardiología",
  "description": "Especialidad dedicada al diagnóstico y tratamiento de enfermedades del corazón",
  "registrationDate": "2025-10-30",
  "consultationCost": 150000.00
}
```

### 3. Crear un Médico

**Endpoint:** `POST /api/doctors`

```json
{
  "identification": "9876543210",
  "firstName": "Dr. Juan",
  "lastName": "López",
  "birthDate": "1975-07-10",
  "phone": "3005555555",
  "email": "juan.lopez@hospital.com",
  "address": "Calle 20 #30-40",
  "gender": "MALE",
  "bloodType": "AB+",
  "licenseNumber": "MED-2020-001",
  "specialtyId": "660e8400-e29b-41d4-a716-446655440001",
  "status": "ACTIVE",
  "consultationFee": 150000.00
}
```

**Respuesta (201 Created):**
```json
{
  "doctorId": "770e8400-e29b-41d4-a716-446655440002",
  "identification": "9876543210",
  "firstName": "Dr. Juan",
  "lastName": "López",
  "birthDate": "1975-07-10",
  "phone": "3005555555",
  "email": "juan.lopez@hospital.com",
  "address": "Calle 20 #30-40",
  "gender": "MALE",
  "bloodType": "AB+",
  "licenseNumber": "MED-2020-001",
  "specialtyId": "660e8400-e29b-41d4-a716-446655440001",
  "registrationDate": "2025-10-30",
  "status": "ACTIVE",
  "consultationFee": 150000.00
}
```

### 4. Crear un Enfermero

**Endpoint:** `POST /api/nurses`

```json
{
  "identification": "5555555555",
  "firstName": "María",
  "lastName": "Rodríguez",
  "birthDate": "1992-11-15",
  "phone": "3007777777",
  "email": "maria.rodriguez@hospital.com",
  "address": "Calle 15 #25-35",
  "gender": "FEMALE",
  "bloodType": "A+",
  "licenseNumber": "ENF-2021-005",
  "status": "ACTIVE",
  "assignedUnit": "Cardiología",
  "hourlyRate": 25000.00
}
```

### 5. Crear una Cita

**Endpoint:** `POST /api/appointments`

```json
{
  "patientId": "550e8400-e29b-41d4-a716-446655440000",
  "doctorId": "770e8400-e29b-41d4-a716-446655440002",
  "appointmentDateTime": "2025-11-15 10:30:00",
  "status": "SCHEDULED",
  "reason": "Consulta de rutina - Dolor en el pecho",
  "notes": "Paciente refiere dolor leve en el pecho desde hace 3 días",
  "cost": 150000.00
}
```

**Respuesta (201 Created):**
```json
{
  "appointmentId": "880e8400-e29b-41d4-a716-446655440003",
  "patientId": "550e8400-e29b-41d4-a716-446655440000",
  "doctorId": "770e8400-e29b-41d4-a716-446655440002",
  "appointmentDateTime": "2025-11-15T10:30:00",
  "registrationDate": "2025-10-30",
  "status": "SCHEDULED",
  "reason": "Consulta de rutina - Dolor en el pecho",
  "notes": "Paciente refiere dolor leve en el pecho desde hace 3 días",
  "cost": 150000.00
}
```

### 6. Crear un Medicamento

**Endpoint:** `POST /api/medicines`

```json
{
  "name": "Aspirina",
  "activeIngredient": "Ácido acetilsalicílico",
  "unitPrice": 5000.00,
  "stockQuantity": 500,
  "manufacturer": "Bayer",
  "expirationDate": "2026-12-31"
}
```

### 7. Crear un Tratamiento

**Endpoint:** `POST /api/treatments`

```json
{
  "patientId": "550e8400-e29b-41d4-a716-446655440000",
  "doctorId": "770e8400-e29b-41d4-a716-446655440002",
  "description": "Tratamiento para hipertensión arterial",
  "medicineIds": "med-001,med-002,med-003",
  "status": "ACTIVE",
  "cost": 500000.00,
  "startDate": "2025-10-30",
  "endDate": "2025-12-30"
}
```

### 8. Crear una Historia Clínica

**Endpoint:** `POST /api/medical-records`

```json
{
  "patientId": "550e8400-e29b-41d4-a716-446655440000",
  "medicalHistory": "Paciente con antecedentes de hipertensión desde hace 5 años",
  "allergies": "Penicilina, Ibuprofeno",
  "chronicDiseases": "Hipertensión arterial, Diabetes tipo 2",
  "treatmentIds": "treat-001,treat-002"
}
```

### 9. Crear una Habitación

**Endpoint:** `POST /api/rooms`

```json
{
  "roomNumber": "301",
  "roomType": "PRIVATE",
  "status": "AVAILABLE",
  "currentPatientId": null,
  "capacity": 1,
  "dailyRate": 300000.00
}
```

**Respuesta (201 Created):**
```json
{
  "roomId": "990e8400-e29b-41d4-a716-446655440004",
  "roomNumber": "301",
  "roomType": "PRIVATE",
  "registrationDate": "2025-10-30",
  "status": "AVAILABLE",
  "currentPatientId": null,
  "capacity": 1,
  "dailyRate": 300000.00
}
```

### 10. Crear una Factura

**Endpoint:** `POST /api/invoices`

```json
{
  "patientId": "550e8400-e29b-41d4-a716-446655440000",
  "subtotal": 650000.00,
  "tax": 123500.00,
  "total": 773500.00,
  "status": "PENDING",
  "description": "Factura por consulta, tratamiento y medicamentos",
  "itemIds": "apt-001,treat-001,med-001"
}
```

## Ejemplos de Consultas GET

### Obtener Todos los Pacientes
```bash
GET /api/patients
```

### Obtener un Paciente Específico
```bash
GET /api/patients/550e8400-e29b-41d4-a716-446655440000
```

### Obtener Médicos por Especialidad
```bash
GET /api/doctors/specialty/660e8400-e29b-41d4-a716-446655440001
```

### Obtener Citas de un Paciente
```bash
GET /api/appointments/patient/550e8400-e29b-41d4-a716-446655440000
```

### Obtener Citas de un Médico
```bash
GET /api/appointments/doctor/770e8400-e29b-41d4-a716-446655440002
```

### Obtener Habitaciones Disponibles
```bash
GET /api/rooms/available
```

### Obtener Habitaciones por Tipo
```bash
GET /api/rooms/type/PRIVATE
```

### Obtener Facturas Pendientes
```bash
GET /api/invoices/status/PENDING
```

### Obtener Facturas de un Paciente
```bash
GET /api/invoices/patient/550e8400-e29b-41d4-a716-446655440000
```

## Ejemplos de Actualización PUT

### Actualizar Estado de Cita
```bash
PUT /api/appointments/880e8400-e29b-41d4-a716-446655440003
```

```json
{
  "patientId": "550e8400-e29b-41d4-a716-446655440000",
  "doctorId": "770e8400-e29b-41d4-a716-446655440002",
  "appointmentDateTime": "2025-11-15 10:30:00",
  "status": "COMPLETED",
  "reason": "Consulta de rutina - Dolor en el pecho",
  "notes": "Paciente evaluado. Se prescribió tratamiento",
  "cost": 150000.00
}
```

### Actualizar Habitación
```bash
PUT /api/rooms/990e8400-e29b-41d4-a716-446655440004
```

```json
{
  "roomNumber": "301",
  "roomType": "PRIVATE",
  "status": "OCCUPIED",
  "currentPatientId": "550e8400-e29b-41d4-a716-446655440000",
  "capacity": 1,
  "dailyRate": 300000.00
}
```

## Ejemplos de Eliminación DELETE

### Eliminar una Cita
```bash
DELETE /api/appointments/880e8400-e29b-41d4-a716-446655440003
```

### Eliminar un Medicamento
```bash
DELETE /api/medicines/med-001
```

### Eliminar un Paciente
```bash
DELETE /api/patients/550e8400-e29b-41d4-a716-446655440000
```

## Códigos de Respuesta HTTP

- **200 OK**: Solicitud exitosa
- **201 Created**: Recurso creado exitosamente
- **204 No Content**: Recurso eliminado exitosamente
- **404 Not Found**: Recurso no encontrado
- **500 Internal Server Error**: Error en el servidor

## Notas Importantes

1. Los IDs se generan automáticamente como UUID
2. Las fechas se almacenan en formato `yyyy-MM-dd`
3. Las fechas y horas se almacenan en formato `yyyy-MM-dd HH:mm:ss`
4. Los datos se persisten en archivos CSV en el directorio `data/`
5. Todos los endpoints soportan CORS (Cross-Origin Resource Sharing)
6. Los valores monetarios se expresan en la moneda local (COP)
