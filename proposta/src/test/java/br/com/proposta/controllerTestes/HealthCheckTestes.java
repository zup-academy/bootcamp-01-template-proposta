package br.com.proposta.controllerTestes;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthCheckTestes {

    @LocalServerPort
    private int port;

    @Test
    public void healthCheckDeveFuncionar(){

        given()
                .basePath("/actuator/health")
                .port(port)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void prometheusDeveFuncionar(){

        given()
                .basePath("/actuator/prometheus")
                .port(port)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void metricsDeveFuncionar(){

        given()
                .basePath("/actuator/metrics")
                .port(port)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());

    }



}
