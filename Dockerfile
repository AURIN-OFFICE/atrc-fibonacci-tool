FROM adoptopenjdk:16-jdk-hotspot as builder

WORKDIR build

COPY src ./src/
COPY gradle ./gradle/
COPY gradlew ./
COPY build.gradle ./
COPY settings.gradle ./

RUN ./gradlew bootJar && cp ./build/libs/*.jar ./application.jar
RUN java -Djarmode=layertools -jar application.jar extract

####
FROM adoptopenjdk:16-jre-hotspot

RUN mkdir -p /atrc_data/metadata && \
    mkdir -p /atrc_data/inputs && \
    mkdir -p /atrc_data/outputs

COPY tool_schema.yml /atrc_data/metadata

WORKDIR application

COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
