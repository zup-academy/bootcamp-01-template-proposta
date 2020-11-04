FROM openjdk:11
ARG JAR_FILE=target/cartao-branco-proposta-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} bootcamp-app-proposta.jar
ENTRYPOINT ["java", "-jar", "bootcamp-app-proposta.jar"]