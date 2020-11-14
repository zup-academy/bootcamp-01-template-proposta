package com.github.marcoscoutozup.proposta.carteira;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.cartao.CartaoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CarteiraServiceTests {

    @Mock
    private CartaoClient cartaoClient;

    @Mock
    private EntityManager entityManager;

    private CarteiraService carteiraService;

    @BeforeEach
    public void setup(){
        initMocks(this);
        carteiraService = new CarteiraService(cartaoClient, entityManager);
    }

    @Test
    @DisplayName("Não deve associar a carteira ao cartão se a resposta do sistema externo não for sucesso")
    public void naoDeveAssociarACarteiraAoCartaoSeARespostaDoSistemaExternoNaoForSucesso(){
        when(cartaoClient.associarCarteira(anyMap(), anyString())).thenReturn(ResponseEntity.badRequest().build());
        ResponseEntity responseEntity = carteiraService.processarAssociacaoDaCarteiraComCartao(null , new Cartao(new String(), null), null, null);
        assertTrue(!responseEntity.getStatusCode().is2xxSuccessful());
    }
}
