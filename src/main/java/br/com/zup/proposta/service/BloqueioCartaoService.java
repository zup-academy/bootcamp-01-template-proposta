package br.com.zup.proposta.service;

import br.com.zup.proposta.integration.IntegracaoCartao;
import br.com.zup.proposta.model.Cartao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BloqueioCartaoService {

    private IntegracaoCartao integracaoCartao;

    @Value("Proposta")
    private String nomeDoSistema;

    public BloqueioCartaoService(IntegracaoCartao integracaoCartao) {
        this.integracaoCartao = integracaoCartao;
    }

    public void bloqueioCartao(Cartao cartao) {
        if(cartao.verificarSeOCartaoEstaBloqueado()) {
            return;
        }

        Map bloqueioRequest = new HashMap();
        bloqueioRequest.put("sistemaResponsavel", nomeDoSistema);
        ResponseEntity responseEntity = integracaoCartao.bloquearCartao(cartao.getNumeroCartao(), bloqueioRequest);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            cartao.bloquearCartao();
            //logger.info("[BLOQUEIO DE CARTÃO] Cartão bloqueado: {}", cartao.getId());
        }
    }
}
