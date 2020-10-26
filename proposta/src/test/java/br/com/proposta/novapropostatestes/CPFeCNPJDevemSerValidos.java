package br.com.proposta.novapropostatestes;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CPFeCNPJDevemSerValidos {


    @LocalServerPort
    private int port;


    @Test
    public void CPFdeveEstarEmFormatoValido() throws JSONException {


        JSONObject novaProposta = new JSONObject()
                .put("nome","Teste Testando")
                .put("email","teste@email.com")
                .put("endereco","Rua Teste, Edifício Insomnia")
                .put("salario",new BigDecimal(10000))
                .put("numeroIdentificacao", "134.241.313-00");


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
    public void CNPJdeveEstarEmFormatoValido() throws JSONException {


        JSONObject novaProposta = new JSONObject()
                .put("nome","Teste Testando")
                .put("email","teste@email.com")
                .put("endereco","Rua Teste, Edifício Insomnia")
                .put("salario",new BigDecimal(10000))
                .put("numeroIdentificacao", "06.990.590/0001-23");


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
    public void CPFInvalidoDeveRetornarBadRequest() throws JSONException {


        JSONObject novaProposta = new JSONObject()
                .put("nome","Teste Testando")
                .put("email","teste@email.com")
                .put("endereco","Rua Teste, Edifício Insomnia")
                .put("salario",new BigDecimal(10000))
                .put("numeroIdentificacao", "134.241.313");


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



    @Test
    public void CNPJInvalidoDeveRetornarBadRequest() throws JSONException {


        JSONObject novaProposta = new JSONObject()
                .put("nome","Teste Testando")
                .put("email","teste@email.com")
                .put("endereco","Rua Teste, Edifício Insomnia")
                .put("salario",new BigDecimal(10000))
                .put("numeroIdentificacao", "06.990.590/0001");


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
