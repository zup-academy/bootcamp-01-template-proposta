package br.com.proposta.services;

import br.com.proposta.dtos.requests.NovoCartaoRequest;
import br.com.proposta.models.Cartao;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.repositories.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class GerarCartaoService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private IntegracaoCartaoService integracaoCartaoService;

    private final Logger logger = LoggerFactory.getLogger(Cartao.class);


    public void geraCartaoSegundoPlano(Proposta proposta, CartaoRepository cartaoRepository){

        HttpStatus respostaCreated = ResponseEntity.status(HttpStatus.CREATED).build().getStatusCode();

        if(integracaoCartaoService.criarCartao(new NovoCartaoRequest(proposta)).getStatusCode() == respostaCreated &&
                cartaoRepository.findByProposta(proposta) == null){

            Cartao cartao = new Cartao(proposta.getNome(), String.valueOf(proposta.getId()));

            cartao.associarComProposta(proposta);

            cartaoRepository.save(cartao);

            logger.info("Cartão criado em segundo plano e associado com a proposta do cliente={}. Identificador do cartão={}",
                    proposta.getNome(), cartao.getId());

        }
    }
}
