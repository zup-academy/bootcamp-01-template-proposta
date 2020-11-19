package com.proposta.criacaocartao;

import com.proposta.criacaoproposta.Proposta;
import com.proposta.criacaoproposta.StatusProposta;
import com.proposta.feign.ApiCartaoCliente;
import com.proposta.feign.response.CartaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class CriacaoCartaoService {

    @Autowired
    EntityManager manager;

    @Autowired
    private ApiCartaoCliente apiCartaoCliente;

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void solicitaCartao() {

        List<Proposta> propostaListaElegivel = manager
                .createQuery("SELECT p from Proposta p WHERE status = :statusProposta AND cartao=null", Proposta.class)
                .setParameter("statusProposta", StatusProposta.ELEGIVEL)
                .getResultList();

        for (Proposta p : propostaListaElegivel) {
            ResponseEntity<CartaoResponse> resposta = apiCartaoCliente.verificarCriacaoCartao(p.getId());
            if (resposta.getStatusCode() == HttpStatus.OK) {
                p.setCartao(resposta.getBody().toModel());
                manager.merge(p);
            }
        }
    }
}
