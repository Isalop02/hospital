Desarrollar una API REST en Java (versión superior a Java 23) utilizando Spring Boot y Lombok, para la gestión operativa de un hospital. El sistema deberá permitir registrar, consultar, actualizar y eliminar información relacionada con las operaciones internas del hospital, incluyendo pacientes, médicos, enfermeros, citas, historias clínicas, tratamientos, habitaciones, facturas, medicamentos y especialidades médicas.

Toda la información se almacenará en un archivo CSV (valores separados por comas) en lugar de una base de datos relacional.

El diseño del proyecto debe evidenciar los principios de la Programación Orientada a Objetos (POO), implementando:
      •     Encapsulamiento para proteger los atributos y controlar su acceso.
      •     Herencia para modelar jerarquías (por ejemplo, una clase abstracta Persona heredada por Médico, Enfermero y Paciente).
      •     Polimorfismo para redefinir o sobrecargar métodos en clases derivadas (por ejemplo, distintos tipos de atención o cálculo de costos según especialidad).
      •     Interfaces para establecer contratos comunes (como Atendible, Registrable o Facturable).
      •     Composición y agregación en las relaciones entre objetos (por ejemplo, una Cita contiene un Paciente y un Médico, y una HistoriaClínica agrupa varios Tratamientos).

El sistema deberá incluir al menos 10 clases dentro de su modelo, pudiendo incorporar records, enumeraciones y clases abstractas según sea necesario para representar comportamientos o estructuras propias del dominio hospitalario.