##FROM openjdk:11->MAS GRANDE
FROM eclipse-temurin:11.0.17_8-jdk-alpine
VOLUME /tmp
ADD ./target/AB-servicio-cursos-V1.jar servicio-cursos.jar
ENTRYPOINT ["java", "-jar","servicio-cursos.jar"]

##como este no tiene puerto fijo quitamos EXPOSE XXXX
##.\mvnw clean package -DskipTests
##docker build -t servicio-cursos:v1 .
##como este NO tiene puerto fijo se lo quitamos cuando lo ejecuta para que lo asigne él 
##docker run -P --name servicio-cursos servicio-cursos:v1
##para crear la bbdd
##docker run -p 3306:3306 --name mysql  -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=microservicios_guzman_productos -v data-mysql:/var/lib/mysql --restart=always mysql:8