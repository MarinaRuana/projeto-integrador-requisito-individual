
FROM maven:3.8.2-jdk-11
COPY . .
RUN mvn -f /pom.xml package
EXPOSE 8080
ENTRYPOINT ["java","-jar","/target/praticafinal-0.0.1-SNAPSHOT.jar"]