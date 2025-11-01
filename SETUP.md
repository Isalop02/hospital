# Guía de Configuración e Instalación

## Requisitos Previos

- **Java**: Versión 17 o superior
- **Maven**: Versión 3.6 o superior
- **Git**: Para clonar el repositorio (opcional)
- **IDE**: IntelliJ IDEA, Eclipse o VS Code (recomendado)

## Verificar Versiones Instaladas

### Verificar Java
```bash
java -version
```

Debe mostrar algo como:
```
openjdk version "17.0.x" 2021-09-14
```

### Verificar Maven
```bash
mvn -version
```

Debe mostrar algo como:
```
Apache Maven 3.8.x
```

## Instalación del Proyecto

### Opción 1: Desde la Línea de Comandos

1. **Navegar al directorio del proyecto**
```bash
cd C:\Users\isalo\IdeaProjects\Hospital
```

2. **Limpiar y compilar el proyecto**
```bash
mvn clean install
```

3. **Ejecutar la aplicación**
```bash
mvn spring-boot:run
```

4. **Acceder a la API**
```
http://localhost:8080/api/health
```

### Opción 2: Desde IntelliJ IDEA

1. **Abrir el proyecto**
   - File → Open → Seleccionar la carpeta Hospital

2. **Esperar a que Maven descargue las dependencias**
   - IntelliJ descargará automáticamente todas las dependencias

3. **Ejecutar la aplicación**
   - Click derecho en `HospitalApplication.java`
   - Seleccionar "Run 'HospitalApplication.main()'"

4. **O usar el botón Run**
   - Presionar Shift + F10 (Windows)

### Opción 3: Desde VS Code

1. **Instalar extensiones recomendadas**
   - Extension Pack for Java
   - Spring Boot Extension Pack

2. **Abrir la carpeta del proyecto**
   - File → Open Folder → Seleccionar Hospital

3. **Ejecutar la aplicación**
   - Presionar F5 o usar el debugger

## Estructura de Directorios

```
Hospital/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── co/edu/umanizales/hospital/
│   │   │       ├── controller/      (Controladores REST)
│   │   │       ├── service/         (Servicios de negocio)
│   │   │       ├── model/           (Clases del modelo)
│   │   │       ├── exception/       (Manejo de excepciones)
│   │   │       ├── util/            (Utilidades)
│   │   │       └── HospitalApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── proyecto.md
│   └── test/
│       └── java/
│           └── co/edu/umanizales/hospital/
│               ├── service/         (Tests de servicios)
│               └── controller/      (Tests de controladores)
├── data/                            (Archivos CSV - creado automáticamente)
├── pom.xml                          (Configuración Maven)
├── README.md                        (Documentación principal)
├── EXAMPLES.md                      (Ejemplos de uso)
├── ARCHITECTURE.md                  (Documentación de arquitectura)
└── SETUP.md                         (Este archivo)
```

## Compilación

### Compilar sin ejecutar tests
```bash
mvn clean install -DskipTests
```

### Compilar con tests
```bash
mvn clean install
```

### Solo compilar (sin empaquetar)
```bash
mvn clean compile
```

## Ejecución

### Ejecutar con Maven
```bash
mvn spring-boot:run
```

### Ejecutar el JAR generado
```bash
java -jar target/Hospital-0.0.1-SNAPSHOT.jar
```

### Ejecutar con propiedades personalizadas
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

## Testing

### Ejecutar todos los tests
```bash
mvn test
```

### Ejecutar un test específico
```bash
mvn test -Dtest=PatientServiceTest
```

### Ejecutar tests con cobertura
```bash
mvn test jacoco:report
```

## Verificación de la Instalación

### 1. Verificar que la aplicación está corriendo
```bash
curl http://localhost:8080/api/health
```

Respuesta esperada:
```json
{
  "status": "UP",
  "service": "Hospital Management API",
  "version": "1.0.0"
}
```

### 2. Obtener información de la API
```bash
curl http://localhost:8080/api/info
```

### 3. Crear un paciente de prueba
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "identification": "1234567890",
    "firstName": "Test",
    "lastName": "Patient",
    "birthDate": "1990-01-01",
    "phone": "3001234567",
    "email": "test@example.com",
    "address": "Test Address",
    "gender": "MALE",
    "bloodType": "O+",
    "emergencyContact": "Emergency",
    "emergencyPhone": "3009876543",
    "status": "ACTIVE"
  }'
```

## Solución de Problemas

### Error: "Java version not supported"
**Solución**: Asegúrate de tener Java 17 o superior instalado
```bash
java -version
```

### Error: "Maven not found"
**Solución**: Agrega Maven al PATH del sistema o usa el mvnw incluido
```bash
./mvnw clean install  (Linux/Mac)
mvnw.cmd clean install (Windows)
```

### Error: "Port 8080 already in use"
**Solución**: Cambia el puerto en `application.properties`
```properties
server.port=9090
```

### Error: "Cannot find symbol" en compilación
**Solución**: Asegúrate de que todas las dependencias están descargadas
```bash
mvn clean install
```

### Error: "CSV files not found"
**Solución**: El directorio `data/` se crea automáticamente en la primera ejecución

## Configuración de IDE

### IntelliJ IDEA

1. **Configurar SDK de Java**
   - File → Project Structure → Project → SDK → Seleccionar Java 17

2. **Habilitar anotaciones de Lombok**
   - File → Settings → Plugins → Buscar "Lombok" → Instalar
   - File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing

3. **Configurar Maven**
   - File → Settings → Build, Execution, Deployment → Build Tools → Maven
   - Verificar que Maven home está configurado

### VS Code

1. **Instalar Extension Pack for Java**
   - Extensions → Buscar "Extension Pack for Java" → Instalar

2. **Instalar Spring Boot Extension Pack**
   - Extensions → Buscar "Spring Boot Extension Pack" → Instalar

3. **Configurar Java Home**
   - Crear archivo `.vscode/settings.json`:
   ```json
   {
     "java.home": "C:\\Program Files\\Java\\jdk-17"
   }
   ```

## Comandos Útiles

### Limpiar caché de Maven
```bash
mvn clean
```

### Descargar dependencias
```bash
mvn dependency:resolve
```

### Ver árbol de dependencias
```bash
mvn dependency:tree
```

### Generar documentación
```bash
mvn javadoc:javadoc
```

### Ejecutar análisis de código
```bash
mvn checkstyle:check
```

## Variables de Entorno (Opcional)

### Windows
```bash
setx JAVA_HOME "C:\Program Files\Java\jdk-17"
setx MAVEN_HOME "C:\Program Files\Apache\maven"
setx PATH "%PATH%;%JAVA_HOME%\bin;%MAVEN_HOME%\bin"
```

### Linux/Mac
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export MAVEN_HOME=/usr/share/maven
export PATH=$PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin
```

## Próximos Pasos

1. Revisar `README.md` para documentación completa
2. Revisar `EXAMPLES.md` para ejemplos de uso
3. Revisar `ARCHITECTURE.md` para entender la arquitectura
4. Explorar los endpoints de la API
5. Ejecutar los tests unitarios
6. Modificar y extender el código según necesidades

## Soporte y Ayuda

- **Documentación de Spring Boot**: https://spring.io/projects/spring-boot
- **Documentación de Lombok**: https://projectlombok.org/
- **Documentación de Maven**: https://maven.apache.org/
- **OpenCSV**: https://opencsv.sourceforge.net/

## Notas Importantes

- Los datos se almacenan en archivos CSV en el directorio `data/`
- Los archivos CSV se crean automáticamente en la primera ejecución
- La aplicación corre en `http://localhost:8080` por defecto
- Todos los endpoints soportan CORS
- Los logs se muestran en la consola con nivel DEBUG para el paquete del hospital
