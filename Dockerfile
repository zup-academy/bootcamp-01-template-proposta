##FROM openjdk:11
##ARG JAR_FILE=target/cartao-branco-proposta-0.0.1-SNAPSHOT.jar
##COPY ${JAR_FILE} bootcamp-app-proposta.jar
##ENTRYPOINT ["java", "-jar", "bootcamp-app-proposta.jar"]

## Builder Image
FROM maven:3.6.3-jdk-11 AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

## Runner Image
FROM openjdk:11
COPY --from=builder /usr/src/app/target/cartao-branco-proposta-0.0.1-SNAPSHOT.jar /usr/app/bootcamp-app-proposta.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/bootcamp-app-proposta.jar"]

#A palavra FROM indica que nossa imagem utiliza como base a imagem do openjdk todos dockerfiles devem ter essa instrução.
#Isso vai nos ajudar, porque perceba, não precisamos declarar a instalação do Java, essa imagem já vem previamente configurada!

#ARG serve como indicação de variável dentro do processo de criação da imagem, uma espécie de variável de ambiente dentro do
#processo de build da imagem.

#Nota a inclusão do comando COPY, esse comando é capaz de copiar o artefato para dentro da imagem e renomeá-lo para ficar
#mais fácil utilização.

#O comando ENTRYPOINT permite que a gente instrua como nossa aplicação vai rodar, no caso o nosso jar.