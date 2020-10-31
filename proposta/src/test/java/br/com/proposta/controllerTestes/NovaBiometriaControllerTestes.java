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
public class NovaBiometriaControllerTestes {

    @LocalServerPort
    private int port;

    @Test
    public void deveRetornarOkAoCriarNovaBiometria() throws JSONException {


        /* não concluído */


        JSONObject novaBiometria = new JSONObject()
                .put("imagemBiometria","imagemBase64");


        /* /biometrias/{cartaoId} */

        given()
                .basePath("/biometrias/c29b096f-f094-4963-ad75-96c4493c2bdb")
                .port(port)
                .header("Content-Type", "application/json")
                .body(novaBiometria.toString())
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());


    }
}
