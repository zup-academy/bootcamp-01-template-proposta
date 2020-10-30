package br.com.proposta.controllerTestes;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class BloqueioCartaoControllerTestes {

    @LocalServerPort
    private int port;

    @Value("${proposta.token.testes}")
    private String token;

    @Test
    public void deveRetornarOkAoCriarNovaProposta() throws JSONException {

        JSONObject novaProposta = new JSONObject()
                .put("sistemaResponsavel","API proposta");

        given()
                .basePath("/bloqueios/96ae33e7-d2b3-42a8-8f71-b282f32fb758")
                .port(port)
                .header("Content-Type", "application/json")
                .body(novaProposta.toString())
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value());

    }
}
