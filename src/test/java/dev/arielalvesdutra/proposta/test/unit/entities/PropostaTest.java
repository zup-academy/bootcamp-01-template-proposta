package dev.arielalvesdutra.proposta.test.unit.entities;

import dev.arielalvesdutra.proposta.entities.Proposta;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static dev.arielalvesdutra.proposta.entities.enums.PropostaStatus.*;
import static dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAnaliseStatus.COM_RESTRICAO;
import static dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAnaliseStatus.SEM_RESTRICAO;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class PropostaTest {

    @Test
    public void statusPadrao_deveSerAguardandoAvalicao() {
        var proposta = new Proposta();

        assertThat(proposta.getStatus()).isEqualTo(AGUARDANDO_AVALIACAO);
    }

    @Test
    public void aplicaResultadoAnalise_resultaComRestricao_deveAtualizarStatusParaNaoElegivel() {
        var proposta = new Proposta();

        proposta.aplicaResultadoAnalise(COM_RESTRICAO);

        assertThat(proposta.getStatus()).isEqualTo(NAO_ELEGIVEL);
    }

    @Test
    public void aplicaResultadoAnalise_resultaSemRestricao_deveAtualizarStatusParaElegivel() {
        var proposta = new Proposta();

        proposta.aplicaResultadoAnalise(SEM_RESTRICAO);

        assertThat(proposta.getStatus()).isEqualTo(ELEGIVEL);
    }

    @Test
    public void aplicaResultadoAnalise_comAgurmentoInvalido_deveLancarException() {
        var proposta = new Proposta();

        try {
            proposta.aplicaResultadoAnalise(null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Argumento inválido para a atualização do status da proposta!");
        }
    }

    @Test
    public void equalsEHashCode_deveSerPorId() {
        var id = UUID.randomUUID().toString();

        var proposta1 = new Proposta().setId(id);
        var proposta2 = new Proposta().setId(id);

        assertThat(proposta1).isEqualTo(proposta2);

        Set<Proposta> propostas = new HashSet<>(asList(proposta1));

        assertThat(propostas).contains(proposta2);
    }
}
