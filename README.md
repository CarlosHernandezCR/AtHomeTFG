# **ATHOME - TFG**

## **Descripción del Proyecto**
ATHOME es una aplicación diseñada para mejorar la convivencia entre compañeros de piso mediante herramientas de organización y comunicación.  
Sus principales funcionalidades incluyen:  
- **Estados de Usuario**: Los miembros del hogar pueden establecer su estado (en casa, fuera, durmiendo) y visualizar el de otros para facilitar la convivencia.  
- **Reserva de Espacios**: Sistema para reservar zonas comunes, donde los miembros pueden votar para aceptar o rechazar una solicitud.  
- **Inventario Personalizado**: Gestión de inmuebles y productos almacenados, con control de acceso a los cajones por parte de sus propietarios.

---

## **Características Principales**
- **Web de Descarga**: Página web simple y atractiva para descargar el APK del cliente móvil.  
- **Seguridad Mejorada**: Uso de tokens para autenticar al cliente móvil y proteger los datos.  
- **Privacidad Total**: Los propietarios de los cajones tienen acceso exclusivo a su contenido.  

---

## **Tecnologías Aplicadas**

### **Frontend**
- **Vue.js**: Framework para construir la interfaz de la web de descarga.  
- **Axios**: Para manejar las llamadas a la API desde la web.  
- **Kotlin**: Desarrollo del cliente móvil con Jetpack Compose.  

### **Backend**
- **Spring Boot**: Framework utilizado para construir la API REST.  
- **Vue 3**: Utilizado para servir la web de descarga desde el servidor.  

### **Base de Datos**
- **SQL (DBeaver)**: Base de datos relacional con modelo Entidad-Relación.  

### **Infraestructura**
- **Docker**: Contenedores para facilitar el despliegue y la portabilidad del proyecto.  

---

## **Arquitectura del Sistema**
- **Cliente Web**: Interfaz en Vue.js para la descarga del APK.  
- **Cliente Móvil**: Aplicación Android desarrollada en Kotlin con Jetpack Compose.  
- **Backend**: API REST en Spring Boot para la lógica del sistema.  
- **Base de Datos**: Sistema relacional basado en SQL para el almacenamiento de datos.  
- **Infraestructura con Docker**: Despliegue sencillo mediante contenedores.  

---

## **Bibliotecas utilizadas**
- **Spring Framework**: Boot, Web, Test, JPA, Validation, Security, Mail, Thymeleaf.  
- **Lombok**: Simplificación del código en el backend.  
- **MySQL Connector**: Integración con la base de datos.  
- **MapStruct**: Conversión entre entidades y DTOs.  
- **jsonwebtoken**: Gestión de tokens para autenticación.  
- **Glassfish Containers**: Contenedores de seguridad.  
- **Jakarta Web API**: Para facilitar el desarrollo web.  

---

## **Requisitos**
### **Software Necesario**
- Node.js y npm  
- Java 17  
- Docker y Docker Compose  

---

## **Instalación**
### **Clonar el repositorio**
```bash
  git clone https://github.com/CarlosHernandezCR/AtHomeTFG.git
```

### **Ejecutar con Docker**
Descargar el servidor desde DockerHub:
```bash
  docker pull carloshernandezcr/inhometfg:latest
```
Iniciar el contenedor:
```bash
  docker-compose up
```
Acceder al Cliente Web
Visitar: http://localhost:8080
