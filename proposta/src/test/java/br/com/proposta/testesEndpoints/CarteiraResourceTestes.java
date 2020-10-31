package br.com.proposta.testesEndpoints;


import io.restassured.response.Response;
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
public class CarteiraResourceTestes {


    @LocalServerPort
    private int port;


    @Test
    public void deveRetornarCreatedAoAssociarCarteira() throws JSONException {


        JSONObject novaAssociacaoCarteira = new JSONObject()
                .put("email","teste@teste.com")
                .put("carteira","paypal");

        /* {cartaoId} */

        given()
                .basePath("/api/carteiras/b4516115-5098-42ae-ab38-c419b5d0537f")
                .port(port)
                .header("Content-Type", "application/json")
                .body(novaAssociacaoCarteira.toString())
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

        return token;
    }

}
