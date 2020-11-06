package com.github.marcoscoutozup.proposta.analisefinanceira;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

public class AnaliseFinanceiraServiceTests {

    @Mock
    private AnaliseFinanceiraClient analiseFinanceiraClient;
    private AnaliseFinanceiraService analiseFinanceiraService;

    @BeforeEach
    public void setup(){
        initMocks(this);
        analiseFinanceiraService = new AnaliseFinanceiraService(analiseFinanceiraClient);
    }

    @Test
    @DisplayName("Não deve realizar análise financeira com proposta nula")
    public void naoDeveRealizarAnaliseFinanceiraComPropostaNula(){
        assertThrows(IllegalArgumentException.class,
                () -> analiseFinanceiraService.processarAnaliseFinanceiraDaProposta(null));
    }

}
