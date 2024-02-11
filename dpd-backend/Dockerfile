FROM adoptopenjdk:11-jdk-hotspot as build
MAINTAINER "domonkos_zoltan"
COPY target/dpd-backend-0.0.1-SNAPSHOT.jar dpd-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/dpd-backend-0.0.1-SNAPSHOT.jar"]