# Etapa de build
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiar pom.xml e baixar dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fonte e buildar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa final
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar o JAR da etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]