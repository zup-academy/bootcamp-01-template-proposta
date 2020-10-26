package br.com.proposta.novapropostatestes;

import br.com.proposta.models.Proposta;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NovaPropostaDeveRetornarOkOuBadRequest {

    /* Requisitos:

    1) documento do solicitante deve ser obrigatório e válido
    2) email não pode ser vazio, nulo ou inválido
    3) nome não pode ser vazio ou nulo
    4) endereço não pode ser vazio ou nulo
    5) salário bruto não pode ser vazio, nulo ou negativo
    6) A proposta deve estar armazenada no sistema, com um identificador gerado pelo sistema.
    7) Retornar 201 com Header Location preenchido com a URL da nova proposta em caso de sucesso.
    8) Retornar 400 quando violado alguma das restrições.

    */

    @LocalServerPort
    private int port;


    @Test
    public void deveRetornarOkAoCriarNovaProposta() throws JSONException {


        JSONObject novaProposta = new JSONObject()
                .put("nome","Teste Testando")
                .put("email","teste@email.com")
                .put("endereco","Rua Teste, Edifício Insomnia")
                .put("salario",new BigDecimal(10000))
                .put("numeroIdentificacao", "123.456.789");


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
    public void deveRetornarBadRequestAoCriarNovaPropostaComViolacaoDeRestricoes() throws JSONException {


        JSONObject novaProposta = new JSONObject()
                .put("nome"," ")
                .put("email","teste")
                .put("endereco","  ")
                .put("salario",new BigDecimal(10000))
                .put("numeroIdentificacao", "123.456.789");


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
