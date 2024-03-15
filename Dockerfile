# Use a imagem base que tenha o Maven e o Java instalados
FROM maven:3.8.4-openjdk-17-slim AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo pom.xml para o diretório de trabalho
COPY pom.xml .

# Baixa as dependências do Maven
RUN mvn dependency:go-offline

# Copia o resto do código-fonte para o diretório de trabalho
COPY src ./src

# Compila o projeto Spring Boot
RUN mvn package -DskipTests

# Define a imagem base para a execução do projeto Spring Boot
FROM openjdk:17-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR gerado para o diretório de trabalho
COPY --from=build /app/target/app.jar .

# Comando para iniciar o aplicativo Spring Boot
CMD ["java", "-jar", "app.jar"]
