FROM openjdk:11.0
WORKDIR /app
##coge el jar de la carpeta target y la deja en la carpeta app
COPY ./target/AB-servicio-alumnos-V1.jar /app/servicio-alumnos.jar 
##esto seria si lo vamos a exponer a un puerto fijo,pero lo dejamos en aleatorio
##EXPOSE 8001 
##lo ejecuta 
ENTRYPOINT ["java","-jar","/servicio-alumnos.jar"]