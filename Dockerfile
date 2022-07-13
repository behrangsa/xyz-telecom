FROM maven:3-eclipse-temurin-17 AS builder

WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine

WORKDIR /app

COPY --from=builder /build/target/xyz-telecom-api-0.0.1-SNAPSHOT.jar /app

COPY entry-point.sh /app/

ENTRYPOINT "/app/entry-point.sh"

