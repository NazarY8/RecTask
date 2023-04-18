FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/scala-2.13/ICEOTask-assembly-0.2.jar /app
COPY config.yaml /app
COPY file.csv /app

RUN apk --no-cache add curl
CMD ["java", "-jar", "ICEOTask-assembly-0.2.jar"]