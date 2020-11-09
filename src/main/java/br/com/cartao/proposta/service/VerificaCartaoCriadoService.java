package br.com.cartao.proposta.service;

import br.com.cartao.proposta.consumer.CriacaoCartaoConsumer;
import br.com.cartao.proposta.domain.response.CartaoResponseSistemaLegado;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Carga intrínseca máxima perimitida - 7
 * Cargaintrínseca da classe - 4
 */

@Service
public class VerificaCartaoCriadoService {

    private static Logger logger = LoggerFactory.getLogger(VerificaCartaoCriadoService.class);
    // +1
    private final CriacaoCartaoConsumer criacaoCartaoConsumer;

    public VerificaCartaoCriadoService(CriacaoCartaoConsumer criacaoCartaoConsumer) {
        this.criacaoCartaoConsumer = criacaoCartaoConsumer;
    }
    // +1
    public Optional<CartaoResponseSistemaLegado> verificaSeCartaoCriado(String idProposta){
        // +1
        try{
            CartaoResponseSistemaLegado cartao = criacaoCartaoConsumer.verificaCartaoCriado(idProposta);
            logger.info("Cartão criado: {}", cartao);

            return Optional.ofNullable(cartao);
        }
        // +1
        catch (FeignException feignException){
            return Optional.empty();
        }
    }
}
