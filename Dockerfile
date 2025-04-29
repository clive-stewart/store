FROM openjdk:17
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "/app/store.jar"]
COPY target/store.jar /app/store.jar
LABEL authors="clivestewart"