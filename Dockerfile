FROM openjdk:17
WORKDIR /app
COPY "target/pidev-0.0.1-SNAPSHOT.jar" "pidev-0.0.1-SNAPSHOT.jar"
EXPOSE 9000
CMD ["java", "-jar", "pidev-0.0.1-SNAPSHOT.jar"]