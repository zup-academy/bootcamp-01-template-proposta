package io.github.evertocnsouza.rest.dto;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MinhaTarefa {

    @Scheduled(fixedDelayString = "${periodicidade.executa-operacao}")
    private void executaOperacao() {
    }
}


