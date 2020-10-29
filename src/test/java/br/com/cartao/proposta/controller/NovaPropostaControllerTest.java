package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.consumer.AnalisePropostaConsumer;
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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = NovaPropostaController.class)
public class NovaPropostaControllerTest {

    private final String urlProposta = "http://localhost:8080/v1/propostas";

    private ObjectMapper objectMapper;

    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalisePropostaConsumer analisePropostaConsumer;

    @MockBean
    private PropostaRepository propostaRepository;

    @InjectMocks
    private NovaPropostaController novaPropostaController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        restTemplate = new TestRestTemplate();
    }

    @Test
    @DisplayName("Deve salvar uma proposta nova quando chamar o método")
    public void deveRetornarSucessoParaNovaProposta() throws Exception {

        NovaPropostaRequest novaPropostaRequest = new NovaPropostaRequest("83794884078","teste@gmail.com","Administrador","Rua governador", BigDecimal.valueOf(800));
        Proposta proposta = novaPropostaRequest.toModel();

        when(propostaRepository.findByDocumento(novaPropostaRequest.getDocumento()))
                .thenReturn(Optional.empty());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity(novaPropostaRequest,httpHeaders);
        ResponseEntity<Void> response = restTemplate.exchange(urlProposta, HttpMethod.POST, httpEntity, Void.class);

        /*RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novaPropostaRequest));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());*/

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());

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

    @Test
    @DisplayName("Deve retornar 400 com nova proposta vazia")
    public void deveRetornar400ComNovaPropostaInvalida(){
        String url = "http://localhost:8080/v1/propostas";
        NovaPropostaRequest novaPropostaRequest = new NovaPropostaRequest("","","","", null);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity(novaPropostaRequest,httpHeaders);

        try{
            ResponseEntity<Void> response = restTemplate.exchange(url,HttpMethod.POST, httpEntity, Void.class);
        }catch (HttpClientErrorException exception){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),exception.getStatusCode().value());
        }


    }


}


