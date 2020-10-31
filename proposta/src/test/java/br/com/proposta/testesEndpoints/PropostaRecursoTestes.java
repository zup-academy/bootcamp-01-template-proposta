package br.com.proposta.testesEndpoints;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class PropostaRecursoTestes {

    @LocalServerPort
    private int port;


    @Test
    public void deveRetornarOkAoCriarNovaProposta() throws JSONException {


        JSONObject novaProposta = new JSONObject()
                .put("nome","Teste Testando")
                .put("email","teste@email.com")
                .put("endereco","Rua Teste, Edifício Insomnia")
                .put("salario",new BigDecimal(10000))
                .put("numeroIdentificacao", "123.126.789");


        given()
                .basePath("/propostas")
                .port(port)
                .header("Content-Type", "application/json")
                .body(novaProposta.toString())
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());

    }


    @Test
    public void deveRetornarBadRequestAoCriarNovaPropostaInvalida() throws JSONException {


        JSONObject novaProposta = new JSONObject()
                .put("nome"," ")
                .put("email","teste@email.com")
                .put("endereco","Rua Teste, Edifício Insomnia")
                .put("salario",new BigDecimal(10000))
                .put("numeroIdentificacao", "123.423.789");


        given()
                .basePath("/propostas")
                .port(port)
                .header("Content-Type", "application/json")
                .body(novaProposta.toString())
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

}
