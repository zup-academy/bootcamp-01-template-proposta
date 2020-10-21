package com.github.marcoscoutozup.proposta.analisefinanceira;

import com.github.marcoscoutozup.proposta.proposta.Proposta;
import com.github.marcoscoutozup.proposta.proposta.enums.StatusDaProposta;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Random;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class AnaliseFinanceiraServiceTests {

    @Mock
    private AnaliseFinanceiraClient analiseFinanceiraClient;
    private AnaliseFinanceiraService analiseFinanceiraService;

    @Mock
    private AnaliseFinanceiraResponse analiseFinanceiraResponse;

    @Mock
    private Logger logger;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        analiseFinanceiraService = new AnaliseFinanceiraService(analiseFinanceiraClient);
    }

    @Test
    @DisplayName("Não deve realizar análise financeira com proposta nula")
    public void naoDeveRealizarAnaliseFinanceiraComPropostaNula(){
        Assert.assertThrows(IllegalArgumentException.class, () -> analiseFinanceiraService.processarAnaliseFinanceiraDaProposta(null));
    }

    @Test
    @DisplayName("Deve realizar análise financeira")
    public void deveRealizarAnaliseFinanceira(){
        when(analiseFinanceiraClient.processaAnaliseFinanceira(any(AnaliseFinanceiraRequest.class))).thenReturn(analiseFinanceiraResponse);
        when(analiseFinanceiraResponse.getResultadoSolicitacao()).thenReturn(gerarStatusProposta());
        Object obj = analiseFinanceiraService.processarAnaliseFinanceiraDaProposta(new Proposta());
        Assert.assertTrue(obj instanceof Proposta);
    }

    private StatusDaProposta gerarStatusProposta(){
        return StatusDaProposta.values()[new Random().nextInt(1 * StatusDaProposta.values().length-1)];
    }
}
