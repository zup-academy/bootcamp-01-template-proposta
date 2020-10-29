package com.github.marcoscoutozup.proposta.analisefinanceira;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class AnaliseFinanceiraServiceTests {

    @Mock
    private AnaliseFinanceiraClient analiseFinanceiraClient;
    private AnaliseFinanceiraService analiseFinanceiraService;

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
    @DisplayName("Não deve realizar análise financeira com resposta nula")
    public void naoDeveRealizarAnaliseFinanceiraComRespostaNula(){
        when(analiseFinanceiraClient.processaAnaliseFinanceira(any())).thenReturn(null);
        Assert.assertThrows(IllegalArgumentException.class, () -> analiseFinanceiraService.processarAnaliseFinanceiraDaProposta(null));
    }
}
