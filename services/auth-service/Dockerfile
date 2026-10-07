# ETAPA 1 Compilación (Build)

FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiar archivos de configuración de Maven para aprovechar la caché de capas
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar omitiendo los testspara acelerar el build
COPY src ./src
RUN mvn clean package -DskipTests


# 2. Entorno de Ejecución (Runtime)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crear un usuario no root por seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copiar el ejecutable JAR desde la ettapa de compilación
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto del microservicio (austar según servicio: 8081, 8082, 8083)
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]