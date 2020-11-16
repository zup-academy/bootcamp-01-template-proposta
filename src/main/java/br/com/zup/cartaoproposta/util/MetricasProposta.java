package br.com.zup.cartaoproposta.util;

import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MetricasProposta {

    private final MeterRegistry meterRegistry;
    private Counter contadorDePropostasCriadas;
    private List<Proposta> propostas;

    public MetricasProposta(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void criarCadastroPropostaContador() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));

        contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada", tags);
    }

    public void incrementarCadastroPropostaContador(){
        contadorDePropostasCriadas.increment();
    }

    public void criarPropostasGauge() {

        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));

        this.meterRegistry.gauge("proposta_gauge", tags, propostas, Collection::size);
    }

    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }

    public void addProposta(Proposta p) {
        propostas.add(p);
    }
}