package br.com.zup.proposta.service;

import br.com.zup.proposta.enums.TipoCarteira;
import br.com.zup.proposta.integration.IntegracaoCartao;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.Carteira;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class AssociaCarteiraService {

    private EntityManager entityManager;
    private IntegracaoCartao integracaoCartao;

    public AssociaCarteiraService(IntegracaoCartao integracaoCartao, EntityManager entityManager) {
        this.integracaoCartao = integracaoCartao;
        this.entityManager = entityManager;
    }

    public ResponseEntity processarAssociacaoComCartao(TipoCarteira tipoCarteira, Cartao cartao, String email, UriComponentsBuilder builder){
        Map carteiraRequest = new HashMap();
        carteiraRequest.put("email", email);
        carteiraRequest.put("carteira", tipoCarteira);
        ResponseEntity responseEntity = integracaoCartao.associarCarteira(cartao.getNumeroCartao(), carteiraRequest);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            Carteira carteira = new Carteira(email, tipoCarteira);
            entityManager.persist(carteira);

            cartao.incluirCarteira(carteira);
            entityManager.merge(cartao);

            URI enderecoConsulta = builder.path("/carteiras/{id}").buildAndExpand(carteira.getId()).toUri();
            return ResponseEntity.created(enderecoConsulta).build();
        }

        return ResponseEntity.badRequest().build();
    }
}
