FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

RUN chmod +x mvnw || true
RUN ./mvnw -q -e -DskipTests package

EXPOSE 8080

CMD ["java", "-jar", "target/*.jar"]
