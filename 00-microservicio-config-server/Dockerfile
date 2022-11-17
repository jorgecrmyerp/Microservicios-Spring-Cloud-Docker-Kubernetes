FROM openjdk:11
WORKDIR /app

##COPY ./target/00-microservicio-config-server-V1.jar .
ADD ./target/00-microservicio-config-server-V1.jar config-server.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar","config-server.jar"]

##ENTRYPOINT ["java", "-jar", "00-microservicio-config-server-V1.jar"]
#### PARA CREARLO-> docker build -t config-server:v1 .
###  PARA EJECUTARLO ->docker build -t config-server:v1 . 