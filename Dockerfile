FROM openjdk:8u181-jre-slim
COPY /build/libs/pc-polls.jar pc-polls.jar
ENTRYPOINT ["java",  "-jar","/pc-polls.jar"]
