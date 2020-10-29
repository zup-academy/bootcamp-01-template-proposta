package br.com.proposta.services;

import br.com.proposta.dtos.requests.BloqueioRequest;
import br.com.proposta.dtos.responses.BloqueioResponse;
import br.com.proposta.dtos.responses.CartaoResponse;
import br.com.proposta.models.Proposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartaoBloqueioService {

    @Autowired
    private IntegracaoCartaoService integracaoCartaoService;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public BloqueioResponse bloquear(String propostaId){


        CartaoResponse cartaoResponse = integracaoCartaoService.buscarCartao(propostaId).getBody();


        BloqueioResponse bloqueioResponse = integracaoCartaoService
                .avisarLegadoBloqueioDoCartao(cartaoResponse.getId(), new BloqueioRequest("api-cartoes")).getBody();


        logger.info("Tentativa de bloqueio do cartão de número={}. Resultado={}",
                cartaoResponse.getId(), bloqueioResponse.getResultado());


        return bloqueioResponse;

    }
}
