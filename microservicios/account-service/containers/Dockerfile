# Utiliza la imagen base oficial de Java 21
FROM openjdk:21

# Copia el archivo .jar de tu aplicación al contenedor
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} account-microservice.jar

# Expone el puerto que tu aplicación utiliza
EXPOSE 8082

# Ejecuta tu aplicación Spring Boot
ENTRYPOINT ["java","-jar","/account-microservice.jar"]

