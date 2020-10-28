package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.domain.request.NovaPropostaRequest;
import br.com.cartao.proposta.repository.PropostaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = NovaPropostaController.class)
public class NovaPropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private PropostaRepository propostaRepository;
    @InjectMocks
    private NovaPropostaController novaPropostaController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Deve salvar uma proposta nova quando chamar o método")
    public void deveRetornarSucessoParaNovaProposta() throws Exception {

        NovaPropostaRequest novaPropostaRequest = new NovaPropostaRequest("274.847.130-07","teste@gmail.com","Administrador","Rua governador", BigDecimal.valueOf(800));
        Proposta proposta = novaPropostaRequest.toModel();

        when(propostaRepository.findByDocumento(novaPropostaRequest.getDocumento())).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novaPropostaRequest));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // verify(propostaRepository).save(proposta);

    }

    @Test
    @DisplayName("Deve retornar 422 quando tentar criar uma nova proposta com doumento já usado")
    public void deveRetornarErroParaNovaPropostaComMesmoDocumento() throws Exception {

        NovaPropostaRequest novaPropostaRequest = new NovaPropostaRequest("274.847.130-07","teste@gmail.com","Administrador","Rua governador", BigDecimal.valueOf(800));

        Proposta proposta = novaPropostaRequest.toModel();

        when(propostaRepository.findByDocumento("274.847.130-07")).thenReturn(Optional.ofNullable(proposta));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novaPropostaRequest));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());


    }


}


