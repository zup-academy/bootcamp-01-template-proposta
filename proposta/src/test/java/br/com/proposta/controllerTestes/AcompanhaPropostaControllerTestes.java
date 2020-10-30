package br.com.proposta.controllerTestes;


import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class AcompanhaPropostaControllerTestes {

    @LocalServerPort
    private int port;

    @Value("${proposta.token.testes}")
    private String token;

    @Test
    public void deveRetornarOkAoCriarNovaProposta() throws JSONException {

        given()
                .basePath("/api/acompanhar-propostas/64b72c05-7f96-4538-88e9-69e3373c2ef3")
                .port(port)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());

    }
}
