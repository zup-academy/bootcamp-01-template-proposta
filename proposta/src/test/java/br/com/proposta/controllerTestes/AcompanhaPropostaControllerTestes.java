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
                .basePath("/api/acompanhar-propostas/b4516115-5098-42ae-ab38-c419b5d0537f")
                .port(port)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());

    }
}
