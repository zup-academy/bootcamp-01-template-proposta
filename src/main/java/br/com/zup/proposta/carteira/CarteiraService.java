package br.com.zup.proposta.carteira;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.IntegracaoCartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

@Service
public class CarteiraService {
    @Autowired
    private IntegracaoCartao integracaoCartao;
    @PersistenceContext
    private EntityManager manager;

    private Logger logger = LoggerFactory.getLogger(CarteiraService.class);

    public ResponseEntity processarAssociacao(Cartao cartao, String email,
                                              TipoCarteira tipoCarteira, UriComponentsBuilder uri) {
        Map carteiraRequest = new HashMap();
        carteiraRequest.put("email", email);
        carteiraRequest.put("carteira", tipoCarteira);

        ResponseEntity responseEntity = integracaoCartao
                .enviarCarteiraAssociada(cartao.getNumeroCartao(), carteiraRequest);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            logger.info("Carteira associada ao cartão de id " + cartao.getId());

            Carteira carteira = new Carteira(email, tipoCarteira);
            manager.persist(carteira);

            cartao.addCarteira(carteira);
            manager.merge(cartao);

            return ResponseEntity.created(uri.path("/carteiras/{id}").buildAndExpand(carteira.getId()).toUri()).build();
        }

        logger.info("Carteira não associada ao cartão de id " + cartao.getId());
        return ResponseEntity.status(responseEntity.getStatusCode()).build();
    }
}
