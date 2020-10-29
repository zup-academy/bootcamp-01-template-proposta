package dev.arielalvesdutra.proposta.test.it.controllers;

import dev.arielalvesdutra.proposta.controllers.dtos.CadastrarBiometriaRequestDTO;
import dev.arielalvesdutra.proposta.controllers.dtos.ErroResponseDTO;
import dev.arielalvesdutra.proposta.entities.Biometria;
import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.http_clients.AnaliseHttpClient;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoAnaliseDTO;
import dev.arielalvesdutra.proposta.repositories.CartaoRepository;
import dev.arielalvesdutra.proposta.services.BiometriaService;
import dev.arielalvesdutra.proposta.services.CartaoService;
import dev.arielalvesdutra.proposta.services.PropostaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static dev.arielalvesdutra.proposta.test.factories.controllers.dtos.CadastrarBiometriaRequestDTOFactory.biometriaInvalida;
import static dev.arielalvesdutra.proposta.test.factories.controllers.dtos.CadastrarBiometriaRequestDTOFactory.biometriaValida;
import static dev.arielalvesdutra.proposta.test.factories.controllers.dtos.CadastrarPropostaDTOFactory.propostaValida;
import static dev.arielalvesdutra.proposta.test.factories.entities.CartaoFactory.cartaoValido;
import static dev.arielalvesdutra.proposta.test.factories.http_clients.dtos.ResultadoAnaliseDTOFactory.resultadoAnaliseSemRestricao;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Testes de Integração para a CartaoController.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CartaoControllerIT {

    @MockBean
    private AnaliseHttpClient analiseHttpClientMock;

    @Autowired
    private BiometriaService biometriaService;

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private Proposta proposta;

    private final String URL_BASE = "/api/cartoes";

    @BeforeEach
    public void setUp() {
        when(analiseHttpClientMock.analisaDadosFinanceiros(any(SolicitacaoAnaliseDTO.class)))
                .thenReturn(resultadoAnaliseSemRestricao());

        proposta = propostaService.cadastrar(propostaValida());
    }

    @AfterEach
    public void tearDown() {
        cartaoService.removeTodos();
        propostaService.removeTodos();
    }

    @Test
    public void cadastrarBiometria_deveRetornarStatus201() {
        var cartaoCadastrado = cadastraCartaoValido();
        var url = URL_BASE + "/" + cartaoCadastrado.getId() + "/biometrias";
        CadastrarBiometriaRequestDTO requestDTO = biometriaValida();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                url,
                requestDTO,
                String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

        String biometriaId = responseEntity.getBody();

        assertThat(biometriaId).isNotNull();

        Biometria biometria = biometriaService.buscaPeloId(biometriaId);

        assertThat(biometria).isNotNull();
        assertThat(biometria.getValor()).isEqualTo(requestDTO.getValor());
        assertThat(biometria.getCartao()).isEqualTo(cartaoCadastrado);
        assertThat(biometria.getCadastradoEm()).isNotNull();

        var headers = responseEntity.getHeaders();
        var locationEsperada = "/api/cartoes/" + cartaoCadastrado.getId() + "/biometrias/" +biometriaId;

        assertThat(headers).isNotNull();
        assertThat(headers.getLocation().getRawPath()).isEqualTo(locationEsperada);
    }

    @Test
    public void cadastrarBiometria_comBiometriaInvalida_deveRetornarStatus400() {
        var cartaoCadastrado = cadastraCartaoValido();
        var requestDTO = biometriaInvalida();
        var url = URL_BASE + "/" + cartaoCadastrado.getId() + "/biometrias";

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.postForEntity(
                url,
                requestDTO,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMensagem()).isEqualTo("Dados inválidos!");
    }

    @Test
    public void cadastrarBiometria_semCartaoCadastrado_deveRetornarStatus404() {
        var requestDTO = biometriaValida();
        var cartaoIdAleatorio = UUID.randomUUID().toString();
        var url = URL_BASE +  "/" + cartaoIdAleatorio + "/biometrias";

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.postForEntity(
                url,
                requestDTO,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMensagem()).isEqualTo("Cartão de ID " + cartaoIdAleatorio +" não localizado!");
    }

    private Cartao cadastraCartaoValido() {
        var cartaoParaCadastrar = cartaoValido(proposta);
        return cartaoRepository.save(cartaoParaCadastrar);
    }
}
