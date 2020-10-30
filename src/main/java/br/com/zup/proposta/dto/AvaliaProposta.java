package br.com.zup.proposta.dto;

import br.com.zup.proposta.dto.integration.Integracoes;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.StatusAvaliacaoProposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AvaliaProposta {

    @Autowired
    private Integracoes integracoes; //1

    private Logger logger = LoggerFactory.getLogger(AvaliaProposta.class);

    public StatusAvaliacaoProposta executar(Proposta novaProposta) //2
            throws JsonProcessingException {

        AvaliacaoPropostaRequest resultadoAvalicao; //3

        try{ //4
             resultadoAvalicao =
                    integracoes.avalia(new AvaliacaoPropostaRequest(novaProposta));

            return resultadoAvalicao.getResultadoSolicitacao().normaliza();

        }catch (FeignException e){ //5

            if (e.status() == 422){ //6
                logger.error("Execção do serviço legado", e.contentUTF8());

                AvaliacaoPropostaRequest propostaRecusada = new ObjectMapper()
                        .readValue(e.contentUTF8(),
                                AvaliacaoPropostaRequest.class);

                return propostaRecusada.getResultadoSolicitacao().normaliza();
            }

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Houve um erro na comunicação com o servidor");
        }

    }

}
