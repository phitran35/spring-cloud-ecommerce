#### Stage 1: Build the application
FROM amazoncorretto:11-alpine-jdk
#WORKDIR application

# Download dockerize and cache that layer
ARG DOCKERIZE_VERSION
RUN wget -O dockerize.tar.gz https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-alpine-linux-amd64-v0.6.1.tar.gz
RUN tar xzf dockerize.tar.gz
RUN chmod +x dockerize

#COPY application/dockerize ./

VOLUME /tmp
EXPOSE 8400
ADD target/*.jar app.jar
ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]