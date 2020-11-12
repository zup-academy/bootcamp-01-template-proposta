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
public class AvisoResourceTestes {


    @LocalServerPort
    private int port;


    @Test
    public void deveRetornarOKAoAvisarViagem() throws JSONException {


        JSONObject novoAviso = new JSONObject()
                .put("destino","Jericoacoara")
                .put("validoAte","2023-10-30");

        /* /api/viagens/{numeroCartao} */

        given()
                .basePath("/api/viagens/e859f81f-9a57-470d-815e-d9bc980fd50a")
                .header("Authorization", getToken())
                .port(port)
                .header("Content-Type", "application/json")
                .body(novoAviso.toString())
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value());

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
