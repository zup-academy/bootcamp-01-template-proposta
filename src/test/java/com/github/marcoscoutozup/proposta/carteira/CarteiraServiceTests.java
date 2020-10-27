package com.github.marcoscoutozup.proposta.carteira;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.cartao.CartaoClient;
import com.github.marcoscoutozup.proposta.carteira.enums.TipoCarteira;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class CarteiraServiceTests {

    @Mock
    private CartaoClient cartaoClient;

    @Mock
    private EntityManager entityManager;

    private CarteiraService carteiraService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        carteiraService = new CarteiraService(cartaoClient, entityManager);
    }

    @Test
    @DisplayName("Não deve associar a carteira ao cartão se a resposta do sistema externo não for sucesso")
    public void naoDeveAssociarACarteiraAoCartaoSeARespostaDoSistemaExternoNaoForSucesso(){
        when(cartaoClient.associarCarteira(anyMap(), any(UUID.class))).thenReturn(ResponseEntity.badRequest().build());
        ResponseEntity responseEntity = carteiraService.processarAssociacaoDaCarteiraComCartao(null , new Cartao(UUID.randomUUID(), null), null, null);
        Assert.assertTrue(!responseEntity.getStatusCode().is2xxSuccessful());
    }
}
