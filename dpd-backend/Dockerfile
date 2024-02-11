# Use maven to build the jar
FROM maven:3.6.3-jdk-11 as buildstage
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src/
RUN mvn clean install -DskipTests

# Then use the result to run the app
FROM adoptopenjdk:11-jdk-hotspot
COPY --from=buildstage /app/target/dpd-backend-0.0.1-SNAPSHOT.jar /dpd-backend-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar","/dpd-backend-0.0.1-SNAPSHOT.jar"]