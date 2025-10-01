# Etapa 1: Compilar a aplicação
FROM maven:3.9.7-eclipse-temurin-21-alpine AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo pom.xml e os arquivos de dependências para o diretório de trabalho
COPY pom.xml ./
COPY domain ./domain
COPY infrastructure ./infrastructure
COPY application ./application

# Compila a aplicação
RUN mvn package

# Etapa 2: Criar a imagem final para execução
FROM eclipse-temurin:21-jre-alpine

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Cria um argumento para o nome da aplicação
ARG JAR_FILE=target/*.jar

# Copia o jar compilado da etapa anterior
# Copia o JAR gerado do módulo application
COPY --from=build /app/infrastructure/target/*.jar app.jar


# Expõe a porta da aplicação
EXPOSE 8080

# Define o comando padrão para rodar a aplicação
CMD ["java", "-jar", "app.jar"]