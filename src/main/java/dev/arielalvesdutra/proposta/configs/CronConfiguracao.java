package dev.arielalvesdutra.proposta.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "cron.habilitada", matchIfMissing = false)
public class CronConfiguracao {
}
