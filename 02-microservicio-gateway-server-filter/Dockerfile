FROM openjdk:11
VOLUME /tmp
EXPOSE 8090
ADD ./target/02-microservicio-gateway-server-V1.jar gateway-server.jar
ENTRYPOINT ["java", "-jar","gateway-server.jar"]


##.\mvnw clean package -DskipTests
##docker build -t gateway-server:v1 .
##docker run -p 8090:8090 --name gateway-server gateway-server:v1