package br.com.itau.cartaobrancoproposta.service;

import br.com.itau.cartaobrancoproposta.client.CartaoClient;
import br.com.itau.cartaobrancoproposta.error.ApiErrorException;
import br.com.itau.cartaobrancoproposta.model.AvisoViagem;
import br.com.itau.cartaobrancoproposta.model.SolicitacaoAvisoViagemCartaoRequest;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AvisoViagemService {

    private final Logger logger = LoggerFactory.getLogger(AvisoViagemService.class);
//1
    private final CartaoClient cartaoClient;

    public AvisoViagemService(CartaoClient cartaoClient) {
        this.cartaoClient = cartaoClient;
    }

    public void notificaAvisoViagem(String numeroCartao, AvisoViagem avisoViagem) { //1
        try { //1
            cartaoClient.avisoViagem(numeroCartao, new SolicitacaoAvisoViagemCartaoRequest(avisoViagem.getDestino(), avisoViagem.getValidoAte())); //1
        } catch (FeignException feignException) { //1 1
            logger.error("Erro na requisição do aviso viagem do cartão com final {}. Motivo: {}", numeroCartao.substring(24), feignException.getMessage());
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Ocorreu um problema na requisição do aviso viagem"); //1
        }
    }
}
