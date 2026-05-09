# 1. Usiamo un'immagine con Maven e Java 21 per compilare
FROM maven:3.8.5-openjdk-21-slim AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. Usiamo un'immagine leggera solo con Java per farlo girare
FROM openjdk:21-jdk-slim
# Copiamo il file .jar generato dalla fase precedente
COPY --from=build /target/*.jar app.jar

# Espone la porta 8080 (quella di Spring Boot)
EXPOSE 8080

# Comando per avviare il server
ENTRYPOINT ["java", "-jar", "app.jar"]