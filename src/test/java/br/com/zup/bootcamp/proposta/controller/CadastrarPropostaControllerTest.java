package br.com.zup.bootcamp.proposta.controller;

import br.com.zup.bootcamp.proposta.api.controller.CadastraPropostaController;
import br.com.zup.bootcamp.proposta.api.dto.RequestPropostaDto;
import br.com.zup.bootcamp.proposta.api.externalsystem.LegadoAnaliseFinaceira;
import br.com.zup.bootcamp.proposta.api.externalsystem.RequestAvaliacaoFinanceiraDto;
import br.com.zup.bootcamp.proposta.api.externalsystem.ResponseAvaliacaoFiananceiraDto;
import br.com.zup.bootcamp.proposta.domain.entity.Proposta;
import br.com.zup.bootcamp.proposta.domain.repository.PropostaRepository;
import br.com.zup.bootcamp.proposta.domain.service.AnalisePropostaStatus;
import br.com.zup.bootcamp.proposta.domain.service.PropostaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CadastrarPropostaControllerTest {

    private static final String DOCUMENTO = "01423348478";
    private static final String ID = "d59345bc-a5f5-41ec-b64b-dc0857a0f73e";
    private static final String EMAIL = "carlos@junior";
    private static final String NOME = "Carlos Eduardo";
    private static final String ENDERECO = "bh";
    private static final BigDecimal SALARAIO = BigDecimal.valueOf(500);

    //classes principais
    protected static Proposta proposta;
    protected static PropostaService service;
    protected static CadastraPropostaController controller;
    protected static PropostaRepository repository = mock(PropostaRepository.class);
    protected static RequestPropostaDto requestProposta = mock(RequestPropostaDto.class);

    //avaliação financeira
    protected static LegadoAnaliseFinaceira analiseFinaceira = mock(LegadoAnaliseFinaceira.class);
    protected static RequestAvaliacaoFinanceiraDto requestAvaliacao;
    protected static ResponseAvaliacaoFiananceiraDto responseAvaliacao;


    @BeforeAll
    public static void setUp() {
        proposta = new Proposta(DOCUMENTO, EMAIL, NOME, ENDERECO,SALARAIO);
        service = new PropostaService(repository, analiseFinaceira);
        controller = new CadastraPropostaController(service, repository);
        when(repository.findByDocumento(DOCUMENTO)).thenReturn(Optional.empty());
        when(requestProposta.toEntity()).thenReturn(proposta);
    }

    @Test
    @DisplayName("Deve salvar um proposta e retornar 201")
    public void teste1(){
        when(analiseFinaceira.SolicitaAnalise(any(RequestAvaliacaoFinanceiraDto.class)))
                .thenReturn(new ResponseAvaliacaoFiananceiraDto(AnalisePropostaStatus.SEM_RESTRICAO));
        when(repository.save(proposta)).thenReturn(proposta);
        ResponseEntity<?> response = controller.adiciona(requestProposta, UriComponentsBuilder.newInstance());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Nao Deve salvar um proposta com documentos iguais e deve retornar 422")
    public void teste2(){
        when(repository.findByDocumento(DOCUMENTO)).thenReturn(Optional.of(proposta));
        ResponseEntity<?> response = controller.adiciona(requestProposta, UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }
}