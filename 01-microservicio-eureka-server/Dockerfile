FROM openjdk:11
VOLUME /tmp
EXPOSE 8761
ADD ./target/01-microservicio-eureka-server-V1.jar eureka-server.jar
ENTRYPOINT ["java", "-jar","eureka-server.jar"]