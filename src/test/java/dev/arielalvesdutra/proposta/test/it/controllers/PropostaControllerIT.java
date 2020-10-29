package dev.arielalvesdutra.proposta.test.it.controllers;

import dev.arielalvesdutra.proposta.controllers.dtos.CadastrarPropostaDTO;
import dev.arielalvesdutra.proposta.controllers.dtos.ErroResponseDTO;
import dev.arielalvesdutra.proposta.controllers.dtos.PropostaResponseDTO;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.entities.enums.PropostaStatus;
import dev.arielalvesdutra.proposta.http_clients.AnaliseHttpClient;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoAnaliseDTO;
import dev.arielalvesdutra.proposta.services.PropostaService;
import dev.arielalvesdutra.proposta.test.factories.http_clients.dtos.ResultadoAnaliseDTOFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static dev.arielalvesdutra.proposta.test.factories.controllers.dtos.CadastrarPropostaDTOFactory.propostaNaoAtendenteAsRestricoes;
import static dev.arielalvesdutra.proposta.test.factories.controllers.dtos.CadastrarPropostaDTOFactory.propostaValida;
import static dev.arielalvesdutra.proposta.test.factories.http_clients.dtos.ResultadoAnaliseDTOFactory.resultadoAnaliseSemRestricao;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Testes de Integração para a PropostaController.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class PropostaControllerIT {

    @MockBean
    private AnaliseHttpClient analiseHttpClientMock;

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private TestRestTemplate restTemplate;

    private String URL_BASE = "/api/propostas";

    @AfterEach
    public void tearDown() {
        propostaService.removeTodos();
    }

    @Test
    public void cadastrar_comPropostaValida_deveGerarPropostaElegivelERetornar201() {
        when(analiseHttpClientMock.analisaDadosFinanceiros(any(SolicitacaoAnaliseDTO.class)))
                .thenReturn(resultadoAnaliseSemRestricao());
        var requestDTO = propostaValida();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

        var propostaId = responseEntity.getBody();

        assertThat(propostaId).isNotNull();

        var proposta = propostaService.buscaPeloId(propostaId);

        assertThat(proposta).isNotNull();
        assertThat(proposta.getId()).isEqualTo(propostaId);
        assertThat(proposta.getNome()).isEqualTo(requestDTO.getNome());
        assertThat(proposta.getDocumento()).isEqualTo(requestDTO.getDocumento());
        assertThat(proposta.getEmail()).isEqualTo(requestDTO.getEmail());
        assertThat(proposta.getEndereco()).isEqualTo(requestDTO.getEndereco());
        assertThat(proposta.getSalario()).isEqualTo(requestDTO.getSalario());
        assertThat(proposta.getStatus()).isEqualTo(PropostaStatus.ELEGIVEL);
        assertThat(proposta.getCadastradoEm()).isNotNull();

        var headers = responseEntity.getHeaders();

        assertThat(headers).isNotNull();
        assertThat(headers.getLocation().getRawPath()).isEqualTo("/api/propostas/" + propostaId);
    }

    @Test
    public void cadastrar_comPropostaQueNaoAtendenteAsRestricoes_deveGerarPropostaNaoElegivelERetornar201() {
        when(analiseHttpClientMock.analisaDadosFinanceiros(any(SolicitacaoAnaliseDTO.class)))
                .thenReturn(ResultadoAnaliseDTOFactory.resultadoAnaliseComRestricao());
        var requestDTO = propostaNaoAtendenteAsRestricoes();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

        var propostaId = responseEntity.getBody();

        assertThat(propostaId).isNotNull();

        var proposta = propostaService.buscaPeloId(propostaId);

        assertThat(proposta).isNotNull();
        assertThat(proposta.getId()).isEqualTo(propostaId);
        assertThat(proposta.getNome()).isEqualTo(requestDTO.getNome());
        assertThat(proposta.getDocumento()).isEqualTo(requestDTO.getDocumento());
        assertThat(proposta.getEmail()).isEqualTo(requestDTO.getEmail());
        assertThat(proposta.getEndereco()).isEqualTo(requestDTO.getEndereco());
        assertThat(proposta.getSalario()).isEqualTo(requestDTO.getSalario());
        assertThat(proposta.getStatus()).isEqualTo(PropostaStatus.NAO_ELEGIVEL);
        assertThat(proposta.getCadastradoEm()).isNotNull();

        var headers = responseEntity.getHeaders();

        assertThat(headers).isNotNull();
        assertThat(headers.getLocation().getRawPath()).isEqualTo("/api/propostas/" + propostaId);
    }

    @Test
    public void cadastrar_semDados_deveRetornar400() {
        var requestDTO = new CadastrarPropostaDTO();

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        ErroResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMensagem()).isEqualTo("Dados inválidos!");
        assertThat(responseDTO.getErros()).asString().contains("Documento é obrigatório!");
        assertThat(responseDTO.getErros()).asString().contains("E-mail é obrigatório!");
        assertThat(responseDTO.getErros()).asString().contains("Nome é obrigatório!");
        assertThat(responseDTO.getErros()).asString().contains("Endereço é obrigatório!");
        assertThat(responseDTO.getErros()).asString().contains("Salário é obrigatório!");
    }

    @Test
    public void cadastrar_comDadosInvalidos_deveRetornar400() {
        var requestDTO = new CadastrarPropostaDTO()
                .setEmail("asdas")
                .setSalario(BigDecimal.ZERO)
                .setDocumento("32");

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        ErroResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMensagem()).isEqualTo("Dados inválidos!");
        assertThat(responseDTO.getErros()).asString().contains("Documento inválido!");
        assertThat(responseDTO.getErros()).asString().contains("E-mail inválido!");
        assertThat(responseDTO.getErros()).asString().contains("Salário deve ser positivo!");
    }

    @Test
    public void cadastrar_comDocumentoDuplicado_deveRetornar422() {
        when(analiseHttpClientMock.analisaDadosFinanceiros(any(SolicitacaoAnaliseDTO.class)))
                .thenReturn(resultadoAnaliseSemRestricao());
        propostaService.cadastrar(propostaValida());
        var requestDTO = propostaValida();

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMensagem()).isEqualTo("Já existe uma proposta cadastrada com o documento " + requestDTO.getDocumento() + "!");
    }

    @Test
    public void cadastrar_semJson_deveRetornar400() {
        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                Void.class,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMensagem()).isEqualTo("Dados inválidos!");
    }

    @Test
    public void buscarProposta_porId_deveRetonarPropostaComStatus200() {
        var proposta = cadastraPropostaElegivel();
        var url = URL_BASE +  "/" + proposta.getId();

        ResponseEntity<PropostaResponseDTO> responseEntity = restTemplate.getForEntity(url, PropostaResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());

        PropostaResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(proposta.getId());
        assertThat(responseDTO.getNome()).isEqualTo(proposta.getNome());
        assertThat(responseDTO.getEmail()).isEqualTo(proposta.getEmail());
        assertThat(responseDTO.getEndereco()).isEqualTo(proposta.getEndereco());
        assertThat(responseDTO.getDocumento()).isEqualTo(proposta.getDocumento());
        assertThat(responseDTO.getSalario()).isEqualTo(proposta.getSalario());
        assertThat(responseDTO.getStatus()).isEqualTo(proposta.getStatus());
        assertThat(responseDTO.getCadastradoEm()).isNotNull();
    }

    @Test
    public void buscarProposta_porId_semPropostaCadastrada_deveRetonarStatus404() {
        var randomId =  UUID.randomUUID().toString();
        var url = URL_BASE +  "/" + randomId;

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.getForEntity(url, ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());

        ErroResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMensagem()).isEqualTo("Proposta com ID " + randomId +" não localizada!");
    }

    private Proposta cadastraPropostaElegivel() {
        when(analiseHttpClientMock.analisaDadosFinanceiros(any(SolicitacaoAnaliseDTO.class)))
                .thenReturn(resultadoAnaliseSemRestricao());
        return propostaService.cadastrar(propostaValida());
    }
}
