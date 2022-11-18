FROM openjdk:11
VOLUME /tmp
ADD ./target/AB-servicio-cursos-V1.jar servicio-cursos.jar
ENTRYPOINT ["java", "-jar","servicio-cursos.jar"]

##como este no tiene puerto fijo quitamos EXPOSE XXXX
##.\mvnw clean package -DskipTests
##docker build -t servicio-cursos:v1 .
##como este NO tiene puerto fijo se lo quitamos cuando lo ejecuta para que lo asigne él 
##docker run -P --name servicio-cursos servicio-cursos:v1