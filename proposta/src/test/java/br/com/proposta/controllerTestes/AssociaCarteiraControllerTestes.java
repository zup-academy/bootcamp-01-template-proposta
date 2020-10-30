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

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class AssociaCarteiraControllerTestes {


    @LocalServerPort
    private int port;


    @Value("${proposta.token.testes}")
    private String token;


    @Test
    public void deveRetornarCreatedAoAssociarCarteira() throws JSONException {


        JSONObject novaAssociacaoCarteira = new JSONObject()
                .put("email","teste@teste.com")
                .put("carteira","paypal");


        given()
                .basePath("/carteiras/fa3995b0-2234-4f30-953c-aba8801dc4af")
                .port(port)
                .header("Content-Type", "application/json")
                .body(novaAssociacaoCarteira.toString())
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());

    }

}
