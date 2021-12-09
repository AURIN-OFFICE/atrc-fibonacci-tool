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

RUN mkdir -p /data/metadata && \
    mkdir -p /data/inputs && \
    mkdir -p /data/outputs

COPY tool_schema.yml /data/metadata

WORKDIR application

COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
