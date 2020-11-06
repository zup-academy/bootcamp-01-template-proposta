package com.github.marcoscoutozup.proposta.biometria;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CadastrarBiometriaControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Cartao cartao;

    @Mock
    private BiometriaRequest request;

    private CadastrarBiometriaController controller;

    @BeforeEach
    public void setup(){
        initMocks(this);
        controller = new CadastrarBiometriaController(entityManager);
    }

    @Test
    @DisplayName("Não deve cadastrar biometria quando cartão não for encontrado")
    public void naoDeveCadastrarBiometriaQuandoCartaoNaoForEncontrado(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(null);
        ResponseEntity responseEntity = controller.cadastrarBiometria(UUID.randomUUID(), null,  new String(), UriComponentsBuilder.newInstance());;
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Não deve cadastrar biometria se cartão não pertence ao solicitante")
    public void naoDeveCadastrarBiometriaSeCartaoNaoPertenceAoSolicitante(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(cartao);
        when(cartao.verificarSeOEmailDoTokenEOMesmoDoCartao(anyString())).thenReturn(false);
        ResponseEntity responseEntity = controller.cadastrarBiometria(UUID.randomUUID(), null,  new String(), UriComponentsBuilder.newInstance());;
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Deve cadastrar biometria")
    public void deveCadastrarBiometria(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(cartao);
        when(cartao.verificarSeOEmailDoTokenEOMesmoDoCartao(anyString())).thenReturn(true);
        when(request.toBiometria()).thenReturn(new Biometria());
        ResponseEntity responseEntity = controller.cadastrarBiometria(UUID.randomUUID(), request,  new String(), UriComponentsBuilder.newInstance());;
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().containsKey("Location") );
    }

}
