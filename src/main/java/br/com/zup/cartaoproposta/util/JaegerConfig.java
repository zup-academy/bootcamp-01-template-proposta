package br.com.zup.cartaoproposta.util;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class JaegerConfig {

    @Bean
    public static JaegerTracer getTracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration config = new Configuration("nosso-cartao-proposta").withSampler(samplerConfig).withReporter(reporterConfig);
        return config.getTracer();
    }
}
