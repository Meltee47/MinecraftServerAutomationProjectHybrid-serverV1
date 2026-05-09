# 1. Fase di Build: Usiamo Maven con Java 21 (Temurin)
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. Fase di Runtime: Usiamo un'immagine leggera con Java 21
FROM eclipse-temurin:21-jdk-jammy
# Copiamo il file .jar generato
COPY --from=build /target/*.jar app.jar

# Esponiamo la porta 8080
EXPOSE 8080

# Comando di avvio
ENTRYPOINT ["java", "-jar", "app.jar"]