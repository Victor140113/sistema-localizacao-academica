
# Etapa 1: compilação da aplicação
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia as configurações e o código-fonte
COPY pom.xml .
COPY src ./src

# Compila e gera o JAR
RUN mvn -B clean package -DskipTests


# Etapa 2: execução da aplicação
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
