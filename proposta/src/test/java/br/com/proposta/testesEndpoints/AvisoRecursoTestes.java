package br.com.proposta.testesEndpoints;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AvisoRecursoTestes {


    @LocalServerPort
    private int port;


    @Test
    public void deveRetornarCreatedAoAvisarViagem() throws JSONException {


        JSONObject novoAviso = new JSONObject()
                .put("destino","Jericoacoara")
                .put("validoAte","2080-10-30");


        given()
                .basePath("/viagens/64b72c05-7f96-4538-88e9-69e3373c2ef3")
                .port(port)
                .header("Content-Type", "application/json")
                .body(novoAviso.toString())
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());

    }
}
