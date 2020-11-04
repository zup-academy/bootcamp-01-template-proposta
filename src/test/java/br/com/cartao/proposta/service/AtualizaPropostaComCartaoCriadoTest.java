package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.enums.EstadoProposta;
import br.com.cartao.proposta.domain.response.CartaoResponseSistemaLegado;
import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.domain.response.VencimentoResponseDto;
import br.com.cartao.proposta.repository.CartaoRepository;
import br.com.cartao.proposta.repository.PropostaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AtualizaPropostaComCartaoCriadoTest {

    private static Proposta proposta1;
    private static Proposta proposta2;
    private static Proposta proposta3;
    private static Proposta proposta4;
    private static EstadoProposta estadoProposta;

    @BeforeEach
    void setUp(){
        proposta1 = new Proposta("862.490.890-63", "teste1@exemplo.com","Endereco1","José Maria", BigDecimal.valueOf(1000));
        proposta2 = new Proposta("900.471.750-10", "teste2@exemplo.com","Endereco2","Fatima Maria", BigDecimal.valueOf(1000));
        proposta3 = new Proposta("274.723.700-10", "teste3@exemplo.com","Endereco3","Josefa Maria", BigDecimal.valueOf(1000));
        proposta4 = new Proposta("446.675.000-93", "teste4@exemplo.com","Endereco4","Carlos Maria", BigDecimal.valueOf(1000));

        estadoProposta = EstadoProposta.ELEGIVEL;

    }

    @Test
    @DisplayName("Deve adicionar o numero do cartao a proposta")
    void deveAdicionarNumeroCartao() {

        VerificaCartaoCriadoService verificaCartaoCriadoService = mock(VerificaCartaoCriadoService.class);
        PropostaRepository propostaRepository = mock(PropostaRepository.class);
        List<Proposta> propostas = List.of(proposta1, proposta2, proposta3, proposta4);
        AtualizaPropostaComCartaoCriado atualizaPropostaComCartaoCriado = new AtualizaPropostaComCartaoCriado(verificaCartaoCriadoService, propostaRepository);
        CartaoResponseSistemaLegado cartao = new CartaoResponseSistemaLegado("123","2020-10-20T14:10:55","Teste",null,null,null,null,BigDecimal.valueOf(100),null,new VencimentoResponseDto("1",10,"2020-10-20T15:30:45"),"abc123");

        when(propostaRepository.findAllByCartaoCriadoFalseAndEstadoProposta(estadoProposta)).thenReturn(propostas);
        when(atualizaPropostaComCartaoCriado.todasPropostasElegiveisSemCartao()).thenReturn(propostas);
        when(verificaCartaoCriadoService.verificaSeCartaoCriado(anyString())).thenReturn(Optional.of(cartao));

        atualizaPropostaComCartaoCriado.atualiza();

        verify(propostaRepository, times(4)).save(any(Proposta.class));
    }

    @Test
    @DisplayName("Não deve adicionar o numero do cartao a proposta")
    void naoDeveAdicionarNumeroCartao() {

        VerificaCartaoCriadoService verificaCartaoCriadoService = mock(VerificaCartaoCriadoService.class);
        PropostaRepository propostaRepository = mock(PropostaRepository.class);
        List<Proposta> propostas = List.of(proposta1, proposta2, proposta3, proposta4);
        AtualizaPropostaComCartaoCriado atualizaPropostaComCartaoCriado = new AtualizaPropostaComCartaoCriado(verificaCartaoCriadoService, propostaRepository);

        when(propostaRepository.findAllByCartaoCriadoFalseAndEstadoProposta(estadoProposta)).thenReturn(propostas);
        when(atualizaPropostaComCartaoCriado.todasPropostasElegiveisSemCartao()).thenReturn(propostas);
        when(verificaCartaoCriadoService.verificaSeCartaoCriado(anyString())).thenReturn(Optional.empty());

        atualizaPropostaComCartaoCriado.atualiza();

        verify(propostaRepository, times(0)).save(any(Proposta.class));
    }

    @Test
    @DisplayName("Não deve percorrer lista vazia de propostas")
    void naoDevePercorrerListaDePropostasAguardandoCartao() {

        VerificaCartaoCriadoService verificaCartaoCriadoService = mock(VerificaCartaoCriadoService.class);
        PropostaRepository propostaRepository = mock(PropostaRepository.class);
        List<Proposta> propostas = List.of();
        AtualizaPropostaComCartaoCriado atualizaPropostaComCartaoCriado = new AtualizaPropostaComCartaoCriado(verificaCartaoCriadoService, propostaRepository);

        when(propostaRepository.findAllByCartaoCriadoFalseAndEstadoProposta(estadoProposta)).thenReturn(propostas);
        when(atualizaPropostaComCartaoCriado.todasPropostasElegiveisSemCartao()).thenReturn(propostas);
        //when(verificaCartaoCriadoService.verificaSeCartaoCriado(anyString())).thenReturn(Optional.empty());

        atualizaPropostaComCartaoCriado.atualiza();

        verify(propostaRepository, times(0)).save(any(Proposta.class));
    }
}