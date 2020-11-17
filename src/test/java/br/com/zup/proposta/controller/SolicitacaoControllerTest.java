package br.com.zup.proposta.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.zup.proposta.configs.ScheduledTask;
import br.com.zup.proposta.controllers.dto.PropostaDto;
import br.com.zup.proposta.controllers.form.SolicitacaoForm;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.EstadoProposta;
import br.com.zup.proposta.service.PropostaService;

import static br.com.zup.proposta.utils.NovaPropostaValida.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class SolicitacaoControllerTest {
    
    @Autowired
    private PropostaService propostaService;
    @SpyBean
    private ScheduledTask scheduledTask;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void novaSolicitacao_201_elegivel() {
        propostaService.removeTudo();

        SolicitacaoForm form = novaSolicitacaoValidaElegivel();
        
        ResponseEntity<PropostaDto> response = restTemplate.postForEntity("/api/propostas", form, PropostaDto.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        PropostaDto propostaResponse = response.getBody();

        assertThat(propostaResponse.getId()).isNotNull();

        Proposta proposta = propostaService.buscarPorId(propostaResponse.getId());

        assertThat(proposta).isNotNull();
        //assertThat(proposta.getDocumento()).isEqualTo(form.getDocumento());
        assertThat(proposta.getEmail()).isEqualTo(form.getEmail());
        assertThat(proposta.getNome()).isEqualTo(form.getNome());
        assertThat(proposta.getEndereco()).isEqualTo(form.getEndereco());
        assertThat(proposta.getSalario()).isEqualTo(form.getSalario());
        assertThat(proposta.getCartaoCriado()).isNotNull();
        assertThat(proposta.getEstadoProposta()).isEqualTo(EstadoProposta.ELEGIVEL);

        assertThat(response.getHeaders()).isNotNull();
        assertThat(response.getHeaders().getLocation().getRawPath()).isEqualTo("/api/propostas/" + proposta.getId());
    }

    @Test
    public void novaSolicitacao_201_naoElegivel() {
        propostaService.removeTudo();

        SolicitacaoForm form = novaSolicitacaoValidaNaoElegivel();

        ResponseEntity<PropostaDto> response = restTemplate.postForEntity("/api/propostas", form, PropostaDto.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        PropostaDto propostaResponse = response.getBody();

        assertThat(propostaResponse.getId()).isNotNull();

        Proposta proposta = propostaService.buscarPorId(propostaResponse.getId());

        assertThat(proposta).isNotNull();
        //assertThat(proposta.getDocumento()).isEqualTo(form.getDocumento());
        assertThat(proposta.getEmail()).isEqualTo(form.getEmail());
        assertThat(proposta.getNome()).isEqualTo(form.getNome());
        assertThat(proposta.getEndereco()).isEqualTo(form.getEndereco());
        assertThat(proposta.getSalario()).isEqualTo(form.getSalario());
        assertThat(proposta.getCartaoCriado()).isNotNull();
        assertThat(proposta.getEstadoProposta()).isEqualTo(EstadoProposta.NAO_ELEGIVEL);

        assertThat(response.getHeaders()).isNotNull();
        assertThat(response.getHeaders().getLocation().getRawPath()).isEqualTo("/api/propostas/" + proposta.getId());
    }

    @Test
    public void novaSolicitacao_duplicada_422() {
        propostaService.removeTudo();

        SolicitacaoForm form = novaSolicitacaoValidaElegivel();

        ResponseEntity<?> response = restTemplate.postForEntity("/api/propostas", form, PropostaDto.class);
        ResponseEntity<?> responseDuplicada = restTemplate.postForEntity("/api/propostas", form, PropostaDto.class);

        assertThat(response).isNotNull();
        assertThat(responseDuplicada).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseDuplicada.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void novaSolicitacao_verificaCartaoCriado_200() {
        propostaService.removeTudo();

        SolicitacaoForm solicitacaoElegivel = novaSolicitacaoValidaElegivel();
        SolicitacaoForm solicitacaoNaoElegivel = novaSolicitacaoValidaNaoElegivel();

        ResponseEntity<PropostaDto> responseElegivel = restTemplate.postForEntity("/api/propostas", 
            solicitacaoElegivel, PropostaDto.class);
        ResponseEntity<PropostaDto> responseNaoElegivel = restTemplate.postForEntity("/api/propostas", 
            solicitacaoNaoElegivel, PropostaDto.class);

        assertThat(responseElegivel.getBody().getId()).isNotNull();
        assertThat(responseNaoElegivel.getBody().getId()).isNotNull();

        Awaitility.await().atMost(25, TimeUnit.SECONDS)
            .untilAsserted(() -> verify(scheduledTask, atLeast(2)).executaCartaoAsync());

        Proposta propostaElegivel = propostaService.buscarPorId(responseElegivel.getBody().getId());
        Proposta propostaNaoElegivel = propostaService.buscarPorId(responseNaoElegivel.getBody().getId());

        assertThat(propostaElegivel.getEstadoProposta()).isEqualTo(EstadoProposta.ELEGIVEL);
        assertThat(propostaNaoElegivel.getEstadoProposta()).isEqualTo(EstadoProposta.NAO_ELEGIVEL);
        assertThat(propostaElegivel.getCartaoCriado()).isEqualTo(true);
        assertThat(propostaNaoElegivel.getCartaoCriado()).isEqualTo(false);
    }
}
