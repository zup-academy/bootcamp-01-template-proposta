package br.com.zup.proposta.metrics;

import br.com.zup.proposta.controller.DetalhePropostaController;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.*;

@Component
public class MinhasMetricas {

    private final MeterRegistry meterRegistry;
    private final Collection<String> strings = new ArrayList<>();
    private final Random random = new Random();
    private final EntityManager entityManager;

    public MinhasMetricas(MeterRegistry meterRegistry, EntityManager entityManager) {
        this.meterRegistry = meterRegistry;
        this.entityManager = entityManager;
        criarGauge();
    }

    //métrica do tipo Counter
    public void meuContador(){

        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itau"));

        Counter contadorDePropostasCriadas =
                this.meterRegistry.counter("proposta_criada", tags);

        contadorDePropostasCriadas.increment();
    }

    //métrica do tipo timer
    //@Scheduled(fixedDelay = 10000)
    public void meuTemporizador(){

        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itau"));

        Timer timerConsultarProposta = this.meterRegistry.timer("consultar_proposta", tags);
        timerConsultarProposta.record( () -> {
            new DetalhePropostaController(entityManager).buscaPropostabyId(UUID.randomUUID());
        });

    }

    //métrica do tipo Gauge
    private void criarGauge() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itau"));

        this.meterRegistry.gauge("meu_gauge", tags, strings, Collection::size);
    }

    public void removeString(){
        strings.removeIf(Objects::nonNull);
    }

    public void addString(){
        strings.add(UUID.randomUUID().toString());
    }

    @Scheduled(fixedDelay = 10000)
    public void simulandoGauge(){
        double randomNumber = random.nextInt();
        if (randomNumber % 2 == 0)
            addString();
        else
            removeString();
    }

}
