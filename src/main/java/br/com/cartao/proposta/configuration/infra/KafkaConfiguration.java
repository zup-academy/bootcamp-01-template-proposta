package br.com.cartao.proposta.configuration.infra;

import br.com.cartao.proposta.domain.event.DadosCartaoProposta;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaConfiguration {

    private final KafkaProperties kafkaProperties;

    public KafkaConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    public Map<String, Object> properties(){
        Map<String, Object> propertiesProducer = new HashMap<>();
        propertiesProducer.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        propertiesProducer.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        propertiesProducer.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return propertiesProducer;
    }

    @Bean
    public ProducerFactory<String, DadosCartaoProposta> producerFactory(){
        return new DefaultKafkaProducerFactory<String, DadosCartaoProposta>(properties());
    }
    @Bean
    public KafkaTemplate<String, DadosCartaoProposta> kafkaTemplate(){
        return new KafkaTemplate(producerFactory());
    }
}
