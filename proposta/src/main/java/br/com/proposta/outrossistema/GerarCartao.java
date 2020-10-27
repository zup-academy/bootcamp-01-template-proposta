package br.com.proposta.outrossistema;

import br.com.proposta.dtos.requests.NovoCartaoRequest;
import br.com.proposta.models.Cartao;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.CartaoRepository;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class GerarCartao {

    @Autowired
    private IntegracaoCartao integracaoCartao;


    public void geraCartaoSegundoPlano(Proposta proposta, CartaoRepository cartaoRepository){

        HttpStatus statusOk = ResponseEntity.status(HttpStatus.OK).build().getStatusCode();

        integracaoCartao.criarCartao(new NovoCartaoRequest(proposta));

        if (integracaoCartao.buscarCartao(proposta.getId()).getStatusCode() == statusOk
        && cartaoRepository.findByIdProposta(String.valueOf(proposta.getId())) == null) {

            Cartao novoCartao = new Cartao(proposta.getNome(), String.valueOf(proposta.getId()));

            cartaoRepository.save(novoCartao);

        }
    }
}
