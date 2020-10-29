package br.com.proposta.services;

import br.com.proposta.dtos.requests.NovoCartaoRequest;
import br.com.proposta.models.Cartao;
import br.com.proposta.models.Proposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;


@Service
public class GerarCartaoService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private IntegracaoCartaoService integracaoCartaoService;

    private final Logger logger = LoggerFactory.getLogger(Cartao.class);


    public void geraCartaoSegundoPlano(Proposta proposta){

        boolean cartaoCriadoNaApiDeContas =
                integracaoCartaoService.criarCartao(new NovoCartaoRequest(proposta)).getStatusCode() == HttpStatus.CREATED;

        if(cartaoCriadoNaApiDeContas && proposta.getCartao() == null){

            Cartao cartao = new Cartao(proposta.getNome(), String.valueOf(proposta.getId()));

            proposta.associaCartao(cartao);

            logger.info("Cartão criado em segundo plano e associado com a proposta do cliente={}", proposta.getNome());

        }
    }
}
