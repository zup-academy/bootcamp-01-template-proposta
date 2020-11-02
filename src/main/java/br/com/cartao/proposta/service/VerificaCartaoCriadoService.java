package br.com.cartao.proposta.service;

import br.com.cartao.proposta.consumer.CriacaoCartaoConsumer;
import br.com.cartao.proposta.domain.model.Cartao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Carga intrínseca máxima perimitida - 7
 * Cargaintrínseca da classe - 3
 */

@Service
public class VerificaCartaoCriadoService {

    private static Logger logger = LoggerFactory.getLogger(VerificaCartaoCriadoService.class);
    // +1
    private final CriacaoCartaoConsumer criacaoCartaoConsumer;

    public VerificaCartaoCriadoService(CriacaoCartaoConsumer criacaoCartaoConsumer) {
        this.criacaoCartaoConsumer = criacaoCartaoConsumer;
    }

    public Optional<Cartao> verificaSeCartaoCriado(String idProposta){
        // +1
        try{
            Cartao cartao = criacaoCartaoConsumer.verificaCartaoCriado(idProposta);
            logger.info("Cartão criado: {}", cartao);

            return Optional.ofNullable(cartao);
        }
        // +1
        catch (FeignException feignException){
            return Optional.empty();
        }
    }
}
