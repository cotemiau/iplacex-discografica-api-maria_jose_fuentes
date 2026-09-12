# STAGE 1: compilar la aplicación con Gradle
FROM gradle:9.0-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle clean bootWar --no-daemon


# STAGE 2: ejecutar la aplicación con OpenJDK
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/build/libs/discografia-1.war app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]