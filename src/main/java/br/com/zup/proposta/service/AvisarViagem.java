package br.com.zup.proposta.service;

import br.com.zup.proposta.dto.integration.IntegracoesCartao;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class AvisarViagem {

    private IntegracoesCartao integracoesCartao;

    public AvisarViagem(IntegracoesCartao integracoesCartao) {
        this.integracoesCartao = integracoesCartao;
    }

    public void notificarAvisoViagem(String numeroCartao, Map<String, String> dadosViagem) {
        try{
            integracoesCartao.notificarAvisoViagem(numeroCartao, dadosViagem);
        }catch (FeignException ex){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O cartão já está notificado para viagem");
        }

    }


}
