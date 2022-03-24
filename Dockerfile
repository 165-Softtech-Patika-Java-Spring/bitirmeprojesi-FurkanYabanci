FROM postgres
ENV POSTGRES_PASSWORD 12345
ENV POSTGRES_DB market

FROM openjdk:11
ARG JAR_FILE=target/market-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8070

ENTRYPOINT ["java", "-jar", "application.jar"]