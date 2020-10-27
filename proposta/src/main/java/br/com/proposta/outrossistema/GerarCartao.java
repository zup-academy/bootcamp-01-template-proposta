package br.com.proposta.outrossistema;

import br.com.proposta.dtos.requests.NovoCartaoRequest;
import br.com.proposta.models.Cartao;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class GerarCartao {


    @Autowired
    private IntegracaoCartao integracaoCartao;


    public void geraCartaoSegundoPlano(Proposta proposta, CartaoRepository cartaoRepository){

        HttpStatus respostaOk = ResponseEntity.status(HttpStatus.CREATED).build().getStatusCode();

        if(integracaoCartao.criarCartao(new NovoCartaoRequest(proposta)).getStatusCode() == respostaOk){

             Cartao cartao = new Cartao(proposta.getNome(), proposta.getId());

             cartaoRepository.save(cartao);

        }
    }
}
