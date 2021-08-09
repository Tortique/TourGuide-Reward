FROM openjdk:8
ARG JAR_FILE=TourGuide-Reward-1.0-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]