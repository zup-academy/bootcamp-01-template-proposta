package br.com.cartao.proposta.service;

import br.com.cartao.proposta.consumer.AvisaBloqueioCartao;
import br.com.cartao.proposta.domain.enums.SolicitacaoBloqueioIntegracaoResponse;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.request.SolicitacaoBloqueioRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 6
 */

@Service
public class AvisoBloqueioIntegracaoService {

    private static String sistemaReferencia = "";
    // +1
    private final AvisaBloqueioCartao avisaBloqueioCartao;

    public AvisoBloqueioIntegracaoService(AvisaBloqueioCartao avisaBloqueioCartao) {
        this.avisaBloqueioCartao = avisaBloqueioCartao;
    }

    public Optional<SolicitacaoBloqueioIntegracaoResponse> avisaSistema(Cartao cartao) throws JsonProcessingException {
        // +1
        SolicitacaoBloqueioRequest solicitacaoBloqueioRequest = new SolicitacaoBloqueioRequest(sistemaReferencia);

        ObjectMapper objectMapper = new ObjectMapper();
        // +1
        try{
            // +1
            SolicitacaoBloqueioIntegracaoResponse solicitacaoBloqueioIntegracaoResponse = avisaBloqueioCartao.avisaBloqueioCartao(cartao.getCartaoId(), solicitacaoBloqueioRequest);
            return Optional.of(solicitacaoBloqueioIntegracaoResponse);
        }
        // +1
        catch (FeignException exception){
            // +1
            if (HttpStatus.UNPROCESSABLE_ENTITY.value() == exception.status()){
                SolicitacaoBloqueioIntegracaoResponse solicitacaoBloqueioIntegracaoResponse = objectMapper.readValue(exception.contentUTF8(), SolicitacaoBloqueioIntegracaoResponse.class);
                return Optional.of(solicitacaoBloqueioIntegracaoResponse);
            }
            return Optional.empty();
        }

    }
}
