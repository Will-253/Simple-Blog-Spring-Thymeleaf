FROM openjdk:21-jdk-slim

LABEL maintainer="William <arthurw253@gmail.com>"

LABEL version="0.0.1"

LABEL description="Simple Blog application running on Spring Boot"

WORKDIR /app

COPY target/SimpleBlog-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

RUN useradd -m myuser

USER myuser

ENTRYPOINT ["java", "-jar", "app.jar"]

CMD []



