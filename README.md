# Requisitos

- 45-50h
	- 2-3h/día

# Objetivos

**Objetivo:** Construir una plataforma distribuida de reservas de eventos escalable, con arquitectura de microservicios, seguridad JWT, despliegue en contenedores (Docker/Kubernetes) y pipeline CI/CD automatizado

**Tema**
- Reservas para una peluquería



# Metodología

- SCRUM

## Backlog


**2. Product Backlog & Planificación de Sprints**

**Sprint 1: Cimientos de la Arquitectura & Core del Dominio (Semana 1)**

- **US-01 (Setup):** Creación del repositorio, estructura de carpetas y plantilla base de `.gitignore`.
    
- **US-02 (Infra Local):** Configuración de `docker-compose.yml` para levantar PostgreSQL de forma independiente para cada servicio.
    
- **US-03 (Event Service):** Implementación del microservicio de eventos (CRUD básico de eventos, gestión de aforo y persistencia).
    
- **US-04 (Auth Service):** Implementación del microservicio de autenticación (registro/login de usuarios y generación de JWT).
    

**Sprint 2: Integración, Lógica Compleja & Frontend (Semana 2)**

- **US-05 (Booking Service):** Desarrollo de la lógica de reservas (control de concurrencia para evitar sobreventa de entradas).
    
- **US-06 (Comunicación API):** Conexión de servicios vía API REST / OpenFeign para validar usuarios e inventario al reservar.
    
- **US-07 (Frontend MVP):** Creación de la interfaz en React + TypeScript para listar eventos y solicitar reservas.
    

**Sprint 3: DevOps, Kubernetes & CI/CD (Semana 3)**

- **US-08 (Dockerización):** Creación de `Dockerfiles` multi-stage optimizados para cada microservicio.
    
- **US-09 (Orquestación K8s):** Redacción de manifiestos de Kubernetes (`Deployments`, `Services`, `ConfigMaps`) para despliegue local.
    
- **US-10 (CI/CD Pipeline):** Configuración de GitHub Actions para ejecutar tests unitarios y build automático en cada _push_


# Stack

- Java Spring Boot
- PostgreSQL
- Docker compose
- Kubernetes
- GitHub CI/CD
- Cloud Deploy AWS
