FROM eclipse-temurin:17
COPY target/amigoscode-api-0.0.1-SNAPSHOT.jar /home/api.jar
CMD ["java","-jar", "/home/api.jar"]