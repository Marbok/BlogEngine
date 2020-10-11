FROM maven:3.6.3-openjdk-11-slim as build

COPY pom.xml .
RUN mvn package
COPY . .

RUN mvn clean package \
    && mkdir ./blog \
    && mv ./target/lib ./blog \
    && mv ./target/blog.jar ./blog

FROM openjdk:11.0.8-jre-slim as running
COPY --from=build ./blog .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "blog.jar"]