#### Stage 1: Build the application
FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
EXPOSE 8060
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]