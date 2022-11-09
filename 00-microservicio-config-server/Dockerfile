FROM openjdk:11
VOLUME /tmp
EXPOSE 8888
ADD ./target/00-microservicio-config-server-V1.jar config-server.jar
ENTRYPOINT ["java", "-jar","config-server.jar"]