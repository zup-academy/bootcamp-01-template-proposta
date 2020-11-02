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
public class AcompanhaPropostaTestes {

    @LocalServerPort
    private int port;

    @Test
    public void deveRetornarOkSolicitarAcompanhamentoDeProposta() throws JSONException {

        /* {idProposta} */

        given()
                .basePath("/api/propostas/55252cf8-e5ac-4f42-b1ea-f1cb419a2da7")
                .header("Authorization", getToken())
                .port(port)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());

    }


    @Test
    public void deveRetornar404SeApropostaNaoExistir() throws JSONException {

        /* {idProposta} */

        given()
                .basePath("/api/propostas/55272cf8-e8ac-4f44-b1ea-f2cb419a2da3")
                .header("Authorization", getToken())
                .port(port)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

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
