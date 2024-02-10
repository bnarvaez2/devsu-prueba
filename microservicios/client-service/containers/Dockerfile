# Utiliza la imagen base oficial de Java 21
FROM openjdk:21

# Copia el archivo .jar de tu aplicación al contenedor
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} client-microservice.jar

# Expone el puerto que tu aplicación utiliza
EXPOSE 8081

# Ejecuta tu aplicación Spring Boot
ENTRYPOINT ["java","-jar","/client-microservice.jar"]

