FROM openjdk:11.0
WORKDIR /app
##coge el jar de la carpeta target y la deja en la carpeta app
COPY ./target/00-microservicio-config-server-V1.jar . 
##esto seria si lo vamos a exponer a un puerto fijo,pero lo dejamos en aleatorio
EXPOSE 8888
##lo ejecuta 
ENTRYPOINT ["java","-jar","/00-microservicio-config-server-V1.jar"]