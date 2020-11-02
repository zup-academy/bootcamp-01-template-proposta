package br.com.proposta.testesEndpoints;

import io.restassured.authentication.OAuthSignature;
import io.restassured.response.Response;
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
public class PropostaResourceTestes {


    @LocalServerPort
    private int port;


    @Test
    public void deveRetornarCreatedAoCriarNovaProposta() throws JSONException {

        JSONObject novaProposta = new JSONObject()
                .put("nome","Teste Testando")
                .put("email","teste@email.com")
                .put("endereco","Rua Teste, Edifício Insomnia")
                .put("salario",new BigDecimal(10000))
                .put("numeroIdentificacao", "013.167.123-11");


        given()
                .basePath("/api/propostas")
                .port(port)
                .header("Content-Type", "application/json")
                .header("Authorization", getToken())
                .body(novaProposta.toString())
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());

    }


    public String getToken() throws JSONException {

        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("username", "bootcamp")
                .formParam("password", "bootcampproposta")
                .formParam("client_id", "nosso-cartao")
                .formParam("client_secret", "")
                .when()
                .post("http://localhost:18080/auth/realms/nosso-cartao/protocol/openid-connect/token");


        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        String accessToken = jsonObject.get("access_token").toString();

        String token = "Bearer " + accessToken;

        System.out.println(token);

        return token;
    }

}
