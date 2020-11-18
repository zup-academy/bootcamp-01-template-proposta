package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.event.DadosCartaoProposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PropostaKafkaProducer {

    private static Logger logger = LoggerFactory.getLogger(PropostaKafkaProducer.class);

    @Value("${topic.cartao-proposta-name}")
    private String topicNamePropostaCartao;

    private final KafkaTemplate<String, DadosCartaoProposta> kafkaTemplate;

    public PropostaKafkaProducer(KafkaTemplate<String, DadosCartaoProposta> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void notifica(DadosCartaoProposta dadosCartaoProposta){
        logger.info("Enviando dados sobre propostaId: {}, cartaoId: {}",dadosCartaoProposta.getIdProposta(), dadosCartaoProposta.getIdCartao());
        kafkaTemplate.send(topicNamePropostaCartao, dadosCartaoProposta);
        logger.info("Evento enviado com sucesso para topico: {}", topicNamePropostaCartao);
    }
}
