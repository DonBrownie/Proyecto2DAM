# Documentación: Análisis del Proyecto de Digitalización Hotelera

**Fecha:** 16 de febrero de 2026  
**Proyecto:** Aplicación de Gestión Integral para Personal Hotelero  

**Jose Angel Ruiz Garceran**  
**Rodrigo Montoya Pastor**  
**German Almagro Egea**

---

# Índice

1. Introducción y justificación  
   - Contexto del proyecto  
   - Justificación de la necesidad  
   - Origen del proyecto  

2. Objetivos del proyecto  
   - Objetivo general del proyecto  
   - Requisitos funcionales  
   - Requisitos no funcionales  

3. Análisis de Viabilidad  
   - Requerimientos técnicos  
   - Análisis DAFO  
   - Seguridad y normativas  
   - Limitaciones técnicas identificadas  

4. Presupuesto económico  
   - Desglose de costes  
   - Ayudas y financiación  

5. Planificación del proyecto  
   - Definición de las fases y actividades  
   - Diagrama de Gantt  

6. Seguimiento del proyecto  

7. Documentación técnica y desarrollo  
   - 7.1 Tecnologías utilizadas  
   - 7.2 Modelo de datos  
   - 7.3 Control de versiones  
   - 7.4 Diseño e implementación de interfaces  
   - 7.5 Acceso a datos y comunicación  
   - 7.6 Seguridad  
   - 7.7 Lógica de negocio  

8. Conclusiones  

9. Bibliografía  

10. Manual de Usuario  

---

# 1. Introducción y justificación

## Contexto del proyecto

El presente proyecto se enmarca en el sector hotelero, concretamente en el ámbito de la gestión interna de recursos humanos. Aunque existen numerosas soluciones tecnológicas orientadas a la gestión de clientes (PMS), reservas y facturación, la comunicación y administración interna del personal continúa, en muchos hoteles pequeños y medianos, dependiendo de procesos manuales o semidigitales poco eficientes.

La propuesta consiste en el desarrollo de una aplicación híbrida compuesta por:

- Una aplicación de escritorio para administración.  
- Una aplicación móvil para empleados.  
- Una base de datos centralizada.  

El objetivo no es únicamente digitalizar procesos, sino centralizar la información laboral en una única plataforma, evitando la fragmentación actual basada en correos electrónicos, mensajería instantánea o documentación en papel.

## Justificación de la necesidad

Se ha detectado una problemática crítica en la gestión analógica:

- **Gestión de nóminas:** Retrasos o extravíos en la entrega de recibos de salarios.  
- **Pérdida de información:** La dependencia del papel conlleva riesgos de pérdida de datos sobre servicios incluidos para los clientes.  
- **Falta de centralización:** La desconexión entre el departamento de administración y el personal operativo impide que la información fluya en tiempo real.  

Resolver esto permitirá:

- **Optimización del flujo de trabajo:** Al centralizar nóminas y servicios en una plataforma, se elimina el tiempo muerto de desplazamiento de los empleados para recoger documentación.  
- **Integridad y Disponibilidad:** Gracias al uso de una base de datos, la información está disponible 24/7.  
- **Coherencia con tendencias actuales del sector:**  

### Transformación Digital
El sector hotelero está migrando hacia el "Paperless" por razones de sostenibilidad y eficiencia.

### Mobile-First para empleados
Existe una tendencia creciente en desarrollar herramientas específicas para el personal que no trabaja en escritorio.

### Seguridad y cumplimiento
La digitalización permite aplicar protocolos de seguridad que el papel no puede ofrecer, cumpliendo con la estricta normativa de protección de datos.

Aunque existen soluciones globales como SAP o Factorial, suelen ser generalistas y presentan costes de licencia inasumibles para hoteles pequeños o medianos. Por otro lado, los PMS se centran en el cliente, pero descuidan la comunicación interna con el empleado. RATHIAN cubre ese hueco como una herramienta especializada, ligera y de bajo coste.

## Origen del proyecto

La idea surge de la experiencia personal de un integrante del equipo (Germán) tras trabajar en un hotel durante el verano. Observó que, mientras la gestión de reservas solía estar digitalizada, la gestión interna de recursos humanos seguía dependiendo de procesos manuales ineficientes.

---

# 2. Objetivos del proyecto

## Objetivo general del proyecto

Desarrollar una aplicación de escritorio y móvil conectada a una base de datos centralizada.

## Requisitos funcionales

### Para el administrador:

- Gestión de Usuarios  
- Gestión de Nóminas  
- Control de Servicios/Reservas  
- Panel de Contabilidad  

### Para el empleado:

- Autenticación  
- Consulta de Nóminas  
- Visualización de Turnos  
- Perfil de Usuario  

## Requisitos no funcionales

- Conectividad segura  
- Control de acceso  
- Interfaz intuitiva  

---

# 3. Análisis de Viabilidad

## Requerimientos técnicos

- Máquinas virtuales  
- IntelliJ  
- Scene Builder  
- JasperReports  
- Firebase  
- GitHub  
- PC principal y secundario  

## Análisis DAFO

### Debilidades
- Falta de experiencia  
- Limitación de tiempo  

### Amenazas
- Vulnerabilidades  
- Obsolescencia  

### Fortalezas
- Conocimiento directo del cliente  
- Uso de frameworks open source  

### Oportunidades
- Transformación digital  

## Seguridad y normativas

- Copias de seguridad periódicas  
- Autenticación multifactor  
- Base de datos local protegida  
- Cifrado en reposo  
- Cumplimiento RGPD  

## Limitaciones técnicas identificadas

- Vinculación base de datos  
- Restricciones presupuestarias  
- Tiempo de ejecución  

---

# 4. Presupuesto económico

## Desglose de costes

- Firebase Blaze Plan (12 meses): 300 €  
- PC administración: 850 €  
- Smartphone Android pruebas: 200 €  
- Dominio y hosting: 120 €  
- Google Play Console: 25 €  
- Consultoría RGPD: 500 €  

**TOTAL ESTIMADO: 15.495 €**

## Ayudas y financiación

- Proyecto Dualiza (hasta 10.000 €)  
- Subvenciones CARM  

---

# 5. Planificación del proyecto

## Fases

### Análisis y Diseño
- Requisitos  
- Casos de uso  
- UI/UX en Figma  
- Modelo de datos  

### Desarrollo
- Infraestructura  
- Backend  
- Frontend  

### Testing
- Conectividad  
- Validación  

### Documentación
- Memoria técnica  
- Manual de usuario  

## Diagrama de Gantt

- Noviembre: Análisis  
- Diciembre: Diseño y desarrollo  
- Enero: Desarrollo y testing  
- Febrero: Testing final y documentación  

---

# 6. Seguimiento del proyecto

Repositorio:
https://github.com/DonBrownie/Proyecto2DAM

---

# 7. Documentación técnica y desarrollo

## 7.1 Tecnologías utilizadas

### Aplicación móvil
- Kotlin 1.9+  
- Android SDK  
- Navigation Component  
- ViewBinding  
- Corrutinas  
- Firebase  

### Aplicación de escritorio
- Java  
- JavaFX  
- Apache Maven  
- MySQL 8.x  
- JasperReports 6.20.6  

### Base de datos y servidor
- XAMPP  
- MySQL/MariaDB  

---

## 7.2 Modelo de datos

### Tabla Usuarios
- id  
- nombre  
- apellido_1  
- apellido_2  
- puesto  
- dni  
- teléfono  

### Tabla Nomina
- id_nomina  
- id_usu  
- pago  
- fecha  

### Tabla contraseña
- id_usu  
- usuario  
- contraseña  

### Tabla reservas
- id_reserva  
- dni_cliente  
- numero_habitacion  
- fecha_inicio  
- fecha_fin  

### Tabla habitacion
- id_habitacion  
- disponible  

---

## 7.3 Control de versiones

https://github.com/DonBrownie/Proyecto2DAM

---

## 7.4 Diseño e implementación

### Android
- Activities y Fragments  
- XML  
- Intents  
- Listeners  

### JavaFX
- FXML  
- Controladores  
- Escenas  
- Separación vista-lógica  

---

## 7.5 Acceso a datos

### Móvil
- HTTP  
- JSON  
- Servidor intermedio  

### Escritorio
- JDBC  
- DAO  
- PreparedStatement  

---

## 7.6 Seguridad

- Consultas preparadas  
- Autenticación  
- Protección de datos  

---

## 7.7 Lógica de negocio

- Registro  
- Autenticación  
- Validación  
- Persistencia  

---

# 8. Conclusiones

- Digitalización de procesos críticos  
- Arquitectura híbrida  
- Seguridad y cumplimiento RGPD  
- Viabilidad económica  
- Consolidación técnica  

---

# 9. Bibliografía

- https://firebase.google.com/docs  
- https://www.youtube.com/watch?v=dpURgJ4HkMk  
- https://www.apachefriends.org/es/index.html  

---

# 10. Manual de Usuario

## Componentes

- Aplicación móvil Android  
- Aplicación escritorio  
- API  
- Servidor MySQL (XAMPP)  

## Orden correcto de inicio

1. Iniciar XAMPP  
2. Activar Apache y MySQL  
3. Ejecutar API  
4. Ejecutar aplicación escritorio  

## Problemas comunes

### Error conexión base de datos
- MySQL activo  
- Base creada  
- Credenciales correctas  

### Error API
- Puerto libre  
- Dependencias correctas  

### No carga datos
- API activa  
- Apache y MySQL activos  

## Cierre

- Cerrar aplicación  
- Detener API  
- Detener Apache y MySQL  
