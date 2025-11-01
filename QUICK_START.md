# Guía Rápida de Inicio

## 🚀 Inicio Rápido en 5 Minutos

### 1. Compilar el Proyecto
```bash
cd C:\Users\isalo\IdeaProjects\Hospital
mvn clean install
```

### 2. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

### 3. Verificar que está corriendo
```bash
curl http://localhost:8080/api/health
```

Deberías ver:
```json
{
  "status": "UP",
  "service": "Hospital Management API",
  "version": "1.0.0"
}
```

## 📋 Comandos Más Usados

### Compilación
```bash
# Compilar sin tests
mvn clean install -DskipTests

# Compilar con tests
mvn clean install

# Solo compilar
mvn clean compile
```

### Ejecución
```bash
# Ejecutar con Maven
mvn spring-boot:run

# Ejecutar JAR
java -jar target/Hospital-0.0.1-SNAPSHOT.jar

# Ejecutar en puerto diferente
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

### Testing
```bash
# Ejecutar todos los tests
mvn test

# Ejecutar test específico
mvn test -Dtest=PatientServiceTest

# Ejecutar sin tests
mvn clean install -DskipTests
```

### Limpieza
```bash
# Limpiar archivos generados
mvn clean

# Limpiar y descargar dependencias
mvn clean dependency:resolve
```

## 🔍 Endpoints Rápidos

### Health Check
```bash
curl http://localhost:8080/api/health
```

### Info de la API
```bash
curl http://localhost:8080/api/info
```

### Crear Paciente
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
    "emergencyContact": "María",
    "emergencyPhone": "3009876543",
    "status": "ACTIVE"
  }'
```

### Obtener Todos los Pacientes
```bash
curl http://localhost:8080/api/patients
```

### Obtener Paciente por ID
```bash
curl http://localhost:8080/api/patients/{patientId}
```

### Actualizar Paciente
```bash
curl -X PUT http://localhost:8080/api/patients/{patientId} \
  -H "Content-Type: application/json" \
  -d '{...}'
```

### Eliminar Paciente
```bash
curl -X DELETE http://localhost:8080/api/patients/{patientId}
```

## 📁 Estructura de Directorios Importante

```
Hospital/
├── src/main/java/co/edu/umanizales/hospital/
│   ├── model/          ← Clases de modelo
│   ├── service/        ← Servicios de negocio
│   ├── controller/     ← Controladores REST
│   └── exception/      ← Manejo de excepciones
├── src/main/resources/
│   └── application.properties  ← Configuración
├── src/test/java/      ← Tests
├── data/               ← Archivos CSV (creado automáticamente)
└── pom.xml             ← Dependencias Maven
```

## 🔧 Configuración Rápida

### Cambiar Puerto
Editar `src/main/resources/application.properties`:
```properties
server.port=9090
```

### Cambiar Nivel de Logging
Editar `src/main/resources/application.properties`:
```properties
logging.level.co.edu.umanizales.hospital=DEBUG
```

## 🧪 Testing Rápido

### Ejecutar todos los tests
```bash
mvn test
```

### Ejecutar test específico
```bash
mvn test -Dtest=PatientServiceTest
```

### Ejecutar test con patrón
```bash
mvn test -Dtest=*ServiceTest
```

## 📊 Entidades Principales

### 1. Paciente (Patient)
```
POST   /api/patients           - Crear
GET    /api/patients           - Listar
GET    /api/patients/{id}      - Obtener
PUT    /api/patients/{id}      - Actualizar
DELETE /api/patients/{id}      - Eliminar
```

### 2. Médico (Doctor)
```
POST   /api/doctors                      - Crear
GET    /api/doctors                      - Listar
GET    /api/doctors/{id}                 - Obtener
GET    /api/doctors/specialty/{specId}   - Por especialidad
PUT    /api/doctors/{id}                 - Actualizar
DELETE /api/doctors/{id}                 - Eliminar
```

### 3. Cita (Appointment)
```
POST   /api/appointments                 - Crear
GET    /api/appointments                 - Listar
GET    /api/appointments/{id}            - Obtener
GET    /api/appointments/patient/{id}    - Por paciente
GET    /api/appointments/doctor/{id}     - Por médico
PUT    /api/appointments/{id}            - Actualizar
DELETE /api/appointments/{id}            - Eliminar
```

### 4. Otros Endpoints
```
/api/nurses              - Enfermeros
/api/specialties         - Especialidades
/api/medicines           - Medicamentos
/api/treatments          - Tratamientos
/api/medical-records     - Historias clínicas
/api/rooms               - Habitaciones
/api/invoices            - Facturas
```

## 🐛 Solución Rápida de Problemas

### Puerto 8080 en uso
```bash
# Cambiar puerto en application.properties
server.port=9090

# O ejecutar en puerto diferente
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

### Dependencias no descargadas
```bash
mvn clean dependency:resolve
```

### Errores de compilación
```bash
# Limpiar y recompilar
mvn clean install

# O sin tests
mvn clean install -DskipTests
```

### CSV no se crea
- El directorio `data/` se crea automáticamente en la primera ejecución
- Verificar permisos de escritura en el directorio del proyecto

## 📚 Documentación Rápida

- **README.md** - Documentación completa
- **EXAMPLES.md** - Ejemplos de uso
- **ARCHITECTURE.md** - Arquitectura del proyecto
- **SETUP.md** - Instalación detallada
- **PROJECT_SUMMARY.md** - Resumen del proyecto
- **VERIFICATION_CHECKLIST.md** - Checklist de verificación

## 🎯 Flujo de Trabajo Típico

### 1. Clonar/Descargar
```bash
cd C:\Users\isalo\IdeaProjects\Hospital
```

### 2. Compilar
```bash
mvn clean install
```

### 3. Ejecutar
```bash
mvn spring-boot:run
```

### 4. Probar
```bash
curl http://localhost:8080/api/health
```

### 5. Desarrollar
- Editar código en `src/main/java`
- Los cambios se recargan automáticamente con Spring Boot DevTools

### 6. Testear
```bash
mvn test
```

## 💡 Tips Útiles

### 1. Usar Postman
- Descargar Postman
- Importar colección desde `Prog3_Hospital.postman_collection.json`
- Probar todos los endpoints

### 2. Ver Logs
- Los logs aparecen en la consola
- Cambiar nivel en `application.properties`

### 3. Verificar CSV
- Los archivos CSV están en `data/`
- Abrirlos con Excel o editor de texto

### 4. Generar IDs
- Los IDs se generan automáticamente como UUID
- No necesitas proporcionar IDs al crear

### 5. Fechas
- Formato: `yyyy-MM-dd`
- Ejemplo: `1990-05-15`

## 🚀 Próximos Pasos

1. ✅ Compilar el proyecto
2. ✅ Ejecutar la aplicación
3. ✅ Probar los endpoints
4. ✅ Crear datos de prueba
5. ✅ Explorar la API
6. ✅ Leer la documentación
7. ✅ Extender el código

## 📞 Soporte

- Revisar `SETUP.md` para problemas de instalación
- Revisar `EXAMPLES.md` para ejemplos de uso
- Revisar `ARCHITECTURE.md` para entender la arquitectura
- Revisar logs en la consola para errores

---

**¡Listo para empezar! 🎉**

Ejecuta: `mvn spring-boot:run`
