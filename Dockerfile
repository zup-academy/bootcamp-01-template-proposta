# Buildar Imagem
FROM maven:3.6.3-jdk-11-slim AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

# Rodar Imagem
FROM openjdk:11-jdk-slim
COPY --from=builder /usr/src/app/target/proposta-0.0.1-SNAPSHOT.jar /usr/app/propostas.jar

ENV spring.application.name=Proposta

ENV DATASOURCE=postgres
ENV DATASOURCE_URL=jdbc:postgresql://localhost:5432/proposta
ENV DATASOURCE_USERNAME=keycloak
ENV DATASOURCE_PASSWORD=password
ENV DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver

ENV KEYCLOAK_ISSUER_URI=http://localhost:18080/auth/realms/nosso-cartao}
ENV KEYCLOAK_JWKS_URI=http://localhost:18080/auth/realms/nosso-cartao/protocol/openid-connect/certs}

ENV URL_ANALISE_FINANCEIRA http://localhost:9999
ENV URL_CARTAO:=http://localhost:8888

EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/propostas.jar"]