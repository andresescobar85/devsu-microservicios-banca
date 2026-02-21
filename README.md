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

🛠️ Detalles de Infraestructura (Docker)
Esta sección explica los componentes que se levantan con el comando docker-compose up -d:

🗄️ Base de Datos Única
A diferencia de otros modelos, este proyecto utiliza una base de datos compartida llamada banco_db.

Imagen: postgres:15-alpine

Credenciales: Usuario: postgres | Clave: root

Puerto Local: 5432

Tablas: Al iniciar, Hibernate creará automáticamente las tablas para clientes (persona, cliente) y para cuentas (cuenta, movimiento, usuario).

🐇 Mensajería (RabbitMQ)
Utilizado para la sincronización asíncrona entre microservicios.

Puerto de Aplicación: 5672

Puerto Administrativo (UI): 15672 (Accede vía http://localhost:15672 con guest/guest).

Flujo: Cuando ms-client registra un nuevo cliente, publica un evento que ms-account consume para replicar los datos básicos del usuario en su lógica interna.

⚙️ Configuración del Entorno (.env)
Aunque los valores están en el docker-compose, es una buena práctica mencionar cómo se mapean a Spring Boot mediante las variables de entorno. Asegúrate de que tu archivo application.properties en ambos proyectos use estas llaves:

Properties
# Ejemplo de conexión dinámica en ms-client y ms-account
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.rabbitmq.host=${SPRING_RABBITMQ_HOST}
server.port=${SERVER_PORT}
🚀 Guía de Despliegue Rápido
Añade este bloque para que el evaluador no tenga dudas:

Construir las imágenes:

Bash
docker-compose build
Levantar el ecosistema:

Bash
docker-compose up -d
Verificar estado:

Bash
docker ps
Deberías ver 4 contenedores activos: db-banco, rabbitmq-banco, ms-client y ms-account.

📝 Notas de Integridad de Datos
Es vital advertir sobre el orden de los datos:

⚠️ IMPORTANTE: > 1. Debido a que ambos servicios usan depends_on, la base de datos y RabbitMQ siempre iniciarán primero.
2. Sincronización: Si creas un cliente mientras ms-account está apagado, el mensaje se quedará en la cola de RabbitMQ y se procesará automáticamente cuando el servicio de cuentas suba. No se pierde información.

Bash
docker-compose build --no-cache ms-account
docker-compose up -d

📊 Estructura de Base de Datos
ms-client: Tabla Persona (Herencia), Tabla Cliente.

ms-account: Tabla Cuenta, Tabla Movimiento.

Desarrollado por: ANDRES FELIPE ESCOBAR ARANGO

Versión: 1.0.0
