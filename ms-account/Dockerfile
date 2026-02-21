FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

COPY . . 
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests -pl ms-account -am

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/ms-account/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]