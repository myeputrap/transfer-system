FROM eclipse-temurin:17

LABEL mentainer = 'myepurba@gmail.com'

WORKDIR /app

COPY target/transfer-system-0.0.1-SNAPSHOT.jar /app/transfer-system.jar

ENTRYPOINT ["java", "-jar", "transfer-system.jar"]