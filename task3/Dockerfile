# Build stage

FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /workspace

COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
COPY src ./src

RUN chmod +x gradlew && ./gradlew build --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

COPY --from=build /workspace .
CMD ["./gradlew", "test"]
