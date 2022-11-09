FROM openjdk:11
VOLUME /tmp
ADD ./target/05-microservicio-validacion-usuarios-V1.jar servicio-validacion.jar
ENTRYPOINT ["java", "-jar","servicio-validacion.jar"]