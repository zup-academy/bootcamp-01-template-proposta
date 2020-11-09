package br.com.zup.proposta.service;

import br.com.zup.proposta.dto.integration.IntegracoesCartao;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class AssociarCarteira {

    private IntegracoesCartao integracoesCartao;

    public AssociarCarteira(IntegracoesCartao integracoesCartao) {
        this.integracoesCartao = integracoesCartao;
    }

    public void notificarAssociacaoCarteira(String numeroCartao,
                                                  Map<String, String> dadosCarteira){
        try{ //1
            integracoesCartao.associarCarteira(numeroCartao, dadosCarteira);
        }catch (FeignException ex){ //2
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Houve um erro ao realizar uma comunicação com o servidor");
        }
    }

}
