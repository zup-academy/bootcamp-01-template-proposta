package br.com.zup.proposta.bloqueio;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.IntegracaoCartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BloqueioCartaoService {

    @Autowired
    private IntegracaoCartao integracaoCartao;
    private Logger logger = LoggerFactory.getLogger(BloqueioCartaoService.class);

    public void bloquearCartao(Cartao cartao) {

        Map bloqueioRequest = new HashMap();
        bloqueioRequest.put("sistemaResponsavel", "Proposta");
        ResponseEntity responseEntity = integracaoCartao.bloquearCartao(cartao.getNumeroCartao(), bloqueioRequest);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cartao.bloquearCartao();
            logger.info("Cartão de id " + cartao.getId() + " bloqueado.");
        }
    }
}
