package br.com.cartao.proposta.service;

import br.com.cartao.proposta.consumer.CriacaoCartaoConsumer;
import br.com.cartao.proposta.domain.response.CartaoResponseSistemaLegado;
import br.com.cartao.proposta.domain.response.VencimentoIntegracaoResponseDto;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

class VerificaCartaoCriadoServiceTest {

    private static String idProposta;

    @BeforeEach
    void setup(){
        idProposta = "1";
    }

    @Test
    @DisplayName("Deve retornar um cartao criado")
    void deveRetornarCartaoCriado(){

        CriacaoCartaoConsumer criacaoCartaoConsumer = mock(CriacaoCartaoConsumer.class);
        VerificaCartaoCriadoIntegracaoService verificaCartaoCriadoService = new VerificaCartaoCriadoIntegracaoService(criacaoCartaoConsumer);
        CartaoResponseSistemaLegado cartao = new CartaoResponseSistemaLegado("123","2020-10-20T14:10:55","Teste",null,null,null,null, BigDecimal.valueOf(100),null,new VencimentoIntegracaoResponseDto("1",10,"2020-10-20T15:30:45"),"abc123");

        when(criacaoCartaoConsumer.verificaCartaoCriado(idProposta)).thenReturn(cartao);

        Optional<CartaoResponseSistemaLegado> cartaoCriado = verificaCartaoCriadoService.verificaSeCartaoCriado(idProposta);

        Assertions.assertEquals(cartao, cartaoCriado.get());
        verify(criacaoCartaoConsumer,times(1)).verificaCartaoCriado(idProposta);

    }

    @Test
    @DisplayName("Deve retornar um cartao criado")
    void naoDeveRetornarCartaoCriado(){

        CriacaoCartaoConsumer criacaoCartaoConsumer = mock(CriacaoCartaoConsumer.class);
        VerificaCartaoCriadoIntegracaoService verificaCartaoCriadoService = new VerificaCartaoCriadoIntegracaoService(criacaoCartaoConsumer);

        Request request = Request.create(Request.HttpMethod.POST, "/api/cartoes", Map.of("content-type", List.of("Application/json")), (byte[]) null,null);

        when(criacaoCartaoConsumer.verificaCartaoCriado(idProposta)).thenThrow(new FeignException.BadRequest("Teste", request,null));

        Optional<CartaoResponseSistemaLegado> cartaoCriado = verificaCartaoCriadoService.verificaSeCartaoCriado(idProposta);

        FeignException feignException = Assertions.assertThrows(FeignException.class, () -> criacaoCartaoConsumer.verificaCartaoCriado(idProposta));
        Assertions.assertEquals(Optional.empty(), cartaoCriado);
        //verify(criacaoCartaoConsumer,times(1)).verificaCartaoCriado(idProposta);
    }

}