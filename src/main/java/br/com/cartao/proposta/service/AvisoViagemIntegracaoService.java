package br.com.cartao.proposta.service;

import br.com.cartao.proposta.consumer.AvisoViagemConsumer;
import br.com.cartao.proposta.domain.model.AvisoViagem;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.request.AvisoViagemIntegracaoRequest;
import br.com.cartao.proposta.domain.response.ResultadoAvisoViagemIntegracao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 8
 */

@Service
public class AvisoViagemIntegracaoService {

    private static Logger logger = LoggerFactory.getLogger(AvisoViagemIntegracaoService.class);
    // +1
    private final AvisoViagemConsumer avisoViagemConsumer;

    public AvisoViagemIntegracaoService(AvisoViagemConsumer avisoViagemConsumer) {
        this.avisoViagemConsumer = avisoViagemConsumer;
    }

    // +3
    public Optional<ResultadoAvisoViagemIntegracao> avisaViagem(Cartao cartao, AvisoViagem avisoViagem) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // +1
        try{
            logger.info("Inicio da tentativa de avisar sistema legado sobre viagem");
            // +1
            AvisoViagemIntegracaoRequest avisoViagemIntegracaoRequest = new AvisoViagemIntegracaoRequest(avisoViagem.getDestinoViagem(), avisoViagem.getTerminaEm());

            ResultadoAvisoViagemIntegracao resultadoAvisoViagemIntegracao = avisoViagemConsumer.avisa(cartao.getCartaoId(), avisoViagemIntegracaoRequest);
            logger.info("Aviso feito com sucesso. Resposta: {}", resultadoAvisoViagemIntegracao.getResultado());
            return Optional.ofNullable(resultadoAvisoViagemIntegracao);
        }
        // +1
        catch (FeignException exception){
            // +1
            if (HttpStatus.UNPROCESSABLE_ENTITY.value() == exception.status()){
                String s = exception.contentUTF8();
                ResultadoAvisoViagemIntegracao resultadoAvisoViagemIntegracao = objectMapper.readValue(s, ResultadoAvisoViagemIntegracao.class);
                logger.info("Tentativa de aviso com falha. Resposta: {}",resultadoAvisoViagemIntegracao.getResultado());
                return Optional.ofNullable(resultadoAvisoViagemIntegracao);
            }
            logger.warn("Erro no envio do aviso sobre viagem. Erro: {}", exception.getMessage());
            return Optional.empty();
        }
    }
}
