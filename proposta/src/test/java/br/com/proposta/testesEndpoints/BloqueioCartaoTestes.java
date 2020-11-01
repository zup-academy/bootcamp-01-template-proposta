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
public class BloqueioCartaoTestes {

    @LocalServerPort
    private int port;

    @Test
    public void deveRetornarOkAoCriarNovaProposta() throws JSONException {

        JSONObject novaProposta = new JSONObject()
                .put("sistemaResponsavel","API proposta");

        /* /api/bloqueios/{cartaoId} */

        given()
                .basePath("/api/bloqueios/15d34c2f-8166-459a-928b-78f363d69695")
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

        return token;
    }

}
