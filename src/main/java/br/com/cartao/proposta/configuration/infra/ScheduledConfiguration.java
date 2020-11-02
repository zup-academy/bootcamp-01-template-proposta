package br.com.cartao.proposta.configuration.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@EnableScheduling
@Configuration
public class ScheduledConfiguration implements SchedulingConfigurer {

    private final int POOL_SIZE = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(POOL_SIZE);
        scheduler.setThreadNamePrefix("taskExecutor");
        scheduler.initialize();

        scheduledTaskRegistrar.setTaskScheduler(scheduler);
    }
}
