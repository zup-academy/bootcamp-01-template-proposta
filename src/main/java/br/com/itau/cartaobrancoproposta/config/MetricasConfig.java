package br.com.itau.cartaobrancoproposta.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricasConfig {

    private final MeterRegistry meterRegistry;

    public MetricasConfig(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void incrementaContador(String nomeDoContador) {
        this.meterRegistry.counter(nomeDoContador).increment();
    }
}
