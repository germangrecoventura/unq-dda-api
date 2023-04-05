FROM gradle:7.6.1-jdk17-alpine AS builder
WORKDIR /artifact

COPY . .
RUN gradle build --no-daemon --stacktrace

FROM amazoncorretto:17.0.6-alpine3.17
WORKDIR /app

EXPOSE 8080
ENV PORT 8080

COPY --from=builder /artifact/build/libs/unq-dda-api*.jar unq-dda-api.jar
CMD ["java", "-jar", "/app/unq-dda-api.jar", "--server.port=${PORT}"]
