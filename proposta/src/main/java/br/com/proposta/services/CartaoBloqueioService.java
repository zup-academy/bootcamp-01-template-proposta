package br.com.proposta.services;

import br.com.proposta.dtos.requests.BloqueioRequest;
import br.com.proposta.dtos.responses.BloqueioResponse;
import br.com.proposta.dtos.responses.CartaoResponse;
import br.com.proposta.models.Proposta;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoBloqueioService {


    @Autowired
    private IntegracaoCartaoService integracaoCartaoService;


    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public BloqueioResponse bloquear(String propostaId){


        String resposta = integracaoCartaoService.buscarCartao(propostaId);

        CartaoResponse cartaoResponse = new Gson().fromJson(resposta, CartaoResponse.class);


        String bloqueio = integracaoCartaoService.bloquearCartao(cartaoResponse.getId(), new BloqueioRequest("api-cartoes"));

        BloqueioResponse bloqueioResponse = new Gson().fromJson(bloqueio, BloqueioResponse.class);


        logger.info("Tentativa de bloqueio do cartão de número={}. Resultado={}",
                cartaoResponse.getId(), bloqueioResponse.getResultado());


        return bloqueioResponse;

    }

}
