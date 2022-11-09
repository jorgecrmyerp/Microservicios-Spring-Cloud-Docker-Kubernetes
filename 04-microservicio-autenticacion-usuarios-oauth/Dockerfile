FROM openjdk:11
VOLUME /tmp
EXPOSE 9100
ADD ./target/04-microservicio-autenticacion-usuarios-oauth-V1.jar servicio-autenticacion.jar
ENTRYPOINT ["java", "-jar","servicio-autenticacion.jar"]