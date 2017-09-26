FROM quay.io/nedoc1990/jdk8-alpine
VOLUME /tmp
ADD target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8081
ENV JAVA_OPT=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /demo.jar