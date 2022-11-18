FROM openjdk:11
WORKDIR /app

EXPOSE 8888
##COPY ./target/00-microservicio-config-server-V1.jar .
ADD ./target/00-microservicio-config-server-V1.jar config-server.jar
ENTRYPOINT ["java", "-jar","config-server.jar"]

##ENTRYPOINT ["java", "-jar", "00-microservicio-config-server-V1.jar"]
##.\mvnw clean package -DskipTests 
#### PARA CREARLO-> docker build -t config-server:v1 .
###  PARA EJECUTARLO ->docker run -p 8888:8888 --name config-server config-server:v1 