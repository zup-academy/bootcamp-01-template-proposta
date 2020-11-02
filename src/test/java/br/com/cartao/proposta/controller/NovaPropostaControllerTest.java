package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.domain.request.NovaPropostaRequest;
import br.com.cartao.proposta.domain.response.NovaPropostaResponseDto;
import br.com.cartao.proposta.repository.PropostaRepository;
import br.com.cartao.proposta.service.NovaPropostaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = NovaPropostaController.class)
public class NovaPropostaControllerTest {

    private final String pathProposta = "/v1/propostas";

    private ObjectMapper objectMapper;

    private UriComponentsBuilder uriComponentsBuilder;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropostaRepository propostaRepository;

    @MockBean
    private NovaPropostaService novaPropostaService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        uriComponentsBuilder = UriComponentsBuilder.newInstance();
    }

    @Test
    @DisplayName("Deve salvar uma proposta nova quando chamar o método")
    public void deveRetornarSucessoParaNovaProposta() throws Exception {

        NovaPropostaRequest novaPropostaRequest = new NovaPropostaRequest("83794884078","teste@gmail.com","Administrador","Rua governador", BigDecimal.valueOf(800));
        Proposta proposta = novaPropostaRequest.toModel();
        NovaPropostaResponseDto novaPropostaResponseDto = new NovaPropostaResponseDto(proposta);
        NovaPropostaController novaPropostaController = new NovaPropostaController(propostaRepository, novaPropostaService);

        when(propostaRepository.findByDocumento(novaPropostaRequest.getDocumento()))
                .thenReturn(Optional.empty());
        when(novaPropostaService.criaNovaProposta(novaPropostaRequest))
                .thenReturn(novaPropostaResponseDto);

        ResponseEntity<?> responseEntity = novaPropostaController.criaNovaProposta(novaPropostaRequest, uriComponentsBuilder);

        Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());

        verify(novaPropostaService,times(1)).criaNovaProposta(novaPropostaRequest);

    }

    @Test
    @DisplayName("Deve retornar 422 quando tentar criar uma nova proposta com doumento já usado")
    public void deveRetornarErroParaNovaPropostaComMesmoDocumento() throws Exception {

        NovaPropostaRequest novaPropostaRequest = new NovaPropostaRequest("274.847.130-07","teste@gmail.com","Administrador","Rua governador", BigDecimal.valueOf(800));
        Proposta proposta = novaPropostaRequest.toModel();
        NovaPropostaController novaPropostaController = new NovaPropostaController(propostaRepository, novaPropostaService);

        when(propostaRepository.findByDocumento(novaPropostaRequest.getDocumento()))
                .thenReturn(Optional.ofNullable(proposta));


        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> novaPropostaController.criaNovaProposta(novaPropostaRequest, uriComponentsBuilder));
        Assertions.assertTrue(runtimeException.getMessage().contains("CPF ou CNPJ já em uso"));

    }

}


