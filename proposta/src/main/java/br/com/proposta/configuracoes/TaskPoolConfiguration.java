package br.com.proposta.configuracoes;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/* Limitando as threads para as requisições assíncronas */

@Configuration
@EnableAsync
public class TaskPoolConfiguration implements AsyncConfigurer {

    private static final int max_pool_size = 100;
    private static final int core_pool_size = 75;
    private static final int queue_capacity = 75;

    @Override
    public Executor getAsyncExecutor(){

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(max_pool_size);
        threadPoolTaskExecutor.setQueueCapacity(queue_capacity);
        threadPoolTaskExecutor.setCorePoolSize(core_pool_size);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setThreadNamePrefix("Gerador de Cartão em segundo plano");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;

    }
}
