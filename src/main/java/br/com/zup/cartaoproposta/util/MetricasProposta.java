package br.com.zup.cartaoproposta.util;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class MetricasProposta {

    private final MeterRegistry meterRegistry;
    private Counter contadorDePropostasCriadas;

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


}