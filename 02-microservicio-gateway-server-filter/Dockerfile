FROM openjdk:11
VOLUME /tmp
EXPOSE 8090
ADD ./target/02-microservicio-gateway-server-filter-V1.jar gateway-server.jar
ENTRYPOINT ["java", "-jar","gateway-server.jar"]