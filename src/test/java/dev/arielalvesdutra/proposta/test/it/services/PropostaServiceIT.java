package dev.arielalvesdutra.proposta.test.it.services;

import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.http_clients.AnaliseHttpClient;
import dev.arielalvesdutra.proposta.http_clients.CartaoHttpClient;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoAnaliseDTO;
import dev.arielalvesdutra.proposta.services.PropostaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static dev.arielalvesdutra.proposta.test.factories.controllers.dtos.CadastrarPropostaDTOFactory.propostaValida;
import static dev.arielalvesdutra.proposta.test.factories.http_clients.dtos.ResultadoAnaliseDTOFactory.resultadoAnaliseSemRestricao;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class PropostaServiceIT {

    @MockBean
    private CartaoHttpClient cartaoHttpClient;

    @MockBean
    private AnaliseHttpClient analiseHttpClientMock;

    @Autowired
    private PropostaService propostaService;

    @AfterEach
    public void tearDown() {
        propostaService.removeTodos();
    }

    @Test
    public void existePeloDocumento_comDocumentoNulo_deveLancarException() {
        try {
            propostaService.existePeloDocumento(null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Documento para busca da proposta não pode ser nulo!");
        }
    }

    @Test
    public void existePeloDocumento_semProposta_deveRetornarFalse() {
        var existePeloDocumento = propostaService.existePeloDocumento("17474920000");

        assertThat(existePeloDocumento).isFalse();
    }

    @Test
    public void buscaTodasElegiveisESemCartao_semCartoesCadastrados_deveRetornarPropostas() {
        when(analiseHttpClientMock.analisaDadosFinanceiros(any(SolicitacaoAnaliseDTO.class)))
                .thenReturn(resultadoAnaliseSemRestricao());
        var propostaCadastrada = propostaService.cadastrar(propostaValida());

        List<Proposta> propostasBuscadas = propostaService.buscaTodasElegiveisESemCartao();

        assertThat(propostasBuscadas).isNotNull();
        assertThat(propostasBuscadas).contains(propostaCadastrada);
    }
}
