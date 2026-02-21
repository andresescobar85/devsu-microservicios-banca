📁 Sistema de Gestión de Cuentas Bancarias (Microservicios)
Este proyecto es una solución de backend basada en microservicios para la gestión de clientes, cuentas y movimientos bancarios, utilizando comunicación asíncrona.

🚀 Tecnologías Utilizadas
Java 17 / Spring Boot 3.x

Bases de Datos: PostgreSQL (ms-client y ms-account)

Mensajería: RabbitMQ (Sincronización de datos entre servicios)

Contenedores: Docker y Docker Compose

Pruebas: JUnit 5 / Mockito

🛠️ Configuración y Despliegue
Sigue estos pasos para poner en marcha el entorno de desarrollo:

1. Requisitos Previos
Tener instalado Docker Desktop y Maven.

Asegurarse de que los puertos 8080, 8081, 8082 y 5672 (RabbitMQ) estén libres.

2. Clonar y Compilar
Desde la raíz del proyecto, compila todos los módulos para generar los archivos .jar:
https://github.com/andresescobar85/devsu-microservicios-banca

Bash
mvn clean compile install -DskipTests
3. Levantar Infraestructura (Docker)
Levanta las bases de datos y el broker de mensajería:

Bash
docker-compose up -d
Nota: Esto iniciará PostgreSQL para ambos servicios y el panel de administración de RabbitMQ en http://localhost:15672 (usuario: guest, clave: guest).

📂 Orden de Ejecución de Pruebas (Postman)
Debido a que existe una dependencia de datos entre servicios, es obligatorio seguir este orden para evitar errores de integridad:

Paso 1: Microservicio de Clientes (ms-client)
Endpoint: POST http://localhost:8080/api/clientes

Acción: Crear los clientes. Al hacerlo, el servicio enviará un mensaje a RabbitMQ para que ms-account registre al usuario.

Paso 2: Microservicio de Cuentas (ms-account)
Endpoint: POST http://localhost:8082/api/cuentas

Acción: Crear las cuentas asociadas a la identificacionCliente creada en el paso anterior.

Paso 3: Microservicio de Movimientos
Endpoint: POST http://localhost:8082/api/movimientos

Acción: Registrar depósitos o retiros. El sistema validará el saldo disponible automáticamente.

Paso 4: Reporte de Estado de Cuenta
Endpoint: GET http://localhost:8082/api/movimientos/reporte

Parámetros: nombre, fechaInicio, fechaFin.

⚠️ Solución de Problemas Comunes
Lista de registros vacía en el reporte:

Verifica que el nombre del cliente en la URL coincida exactamente con el de la base de datos (ojo con mayúsculas y espacios).

Asegúrate de que el rango de fechas incluya el día del movimiento.

Error de Compilación (Incompatible Types):

Si cambiaste un parámetro de Long a String, recuerda ejecutar mvn clean compile para borrar los archivos .class antiguos.

Docker no reconoce cambios en el código:

Si modificaste el código Java, debes reconstruir la imagen:

Bash
docker-compose build --no-cache ms-account
docker-compose up -d
📊 Estructura de Base de Datos
ms-client: Tabla Persona (Herencia), Tabla Cliente.

ms-account: Tabla Cuenta, Tabla Movimiento.

Desarrollado por: [Tu Nombre]

Versión: 1.0.0
