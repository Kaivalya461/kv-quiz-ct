FROM amazoncorretto:21-alpine
COPY target/kv-quiz-*.jar app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=gke,prod","-jar","/app.jar"]
