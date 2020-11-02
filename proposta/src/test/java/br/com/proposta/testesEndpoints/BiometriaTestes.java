package br.com.proposta.testesEndpoints;

import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BiometriaTestes {


    @LocalServerPort
    private int port;


    @Test
    public void deveriaCadastrarBiometriaComSucesso() throws IOException, JSONException {

        /* coloquei um arquivo txt só para testar o encode e decode [string <-> byte[]] */

        byte[] conteudoArquivo = FileUtils
                .readFileToByteArray(new File("/home/marceloamorim/Documentos/bootcamp-01-template-proposta/proposta/src/main/resources/static/teste.txt"));

        String stringEncodada = Base64.getEncoder().encodeToString(conteudoArquivo);

        /* {numeroCartao} */

        JSONObject novaBiometria = new JSONObject()
                .put("imagemBiometria", stringEncodada);

        given()
                .basePath("/api/biometrias/e859f81f-9a57-470d-815e-d9bc980fd50a")
                .header("Content-Type", "application/json")
                .header("Authorization", getToken())
                .port(port)
                .body(novaBiometria.toString())
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
