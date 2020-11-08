package br.com.zup.proposta.services;

import br.com.zup.proposta.dto.integration.IntegracoesCartao;
import br.com.zup.proposta.model.enums.StatusCartao;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@Service
public class SolicitarBloqueio {

    private IntegracoesCartao integracoesCartao;

    public SolicitarBloqueio(IntegracoesCartao integracoesCartao) {
        this.integracoesCartao = integracoesCartao;
    }

    public Map<String, String> bloquear(String numeroCartao) {

        try{
            return integracoesCartao.bloquearCartao(numeroCartao,
                    Map.of("sistemaResponsavel ", "Aplicação proposta"));

        }catch(FeignException ex){
            //quebra de regra de negócio?
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Esse cartão já foi bloqueado");
        }

    }


}
