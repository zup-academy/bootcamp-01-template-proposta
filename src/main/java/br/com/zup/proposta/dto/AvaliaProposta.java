package br.com.zup.proposta.dto;

import br.com.zup.proposta.dto.integration.IntegracoesAnaliseFinanceira;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.StatusAvaliacaoProposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AvaliaProposta {

    //services devem ter até 7 pontos de carga insentrica?

    private IntegracoesAnaliseFinanceira integracoesAnaliseFinanceira; //1

    public AvaliaProposta(IntegracoesAnaliseFinanceira integracoesAnaliseFinanceira) {
        this.integracoesAnaliseFinanceira = integracoesAnaliseFinanceira;
    }

    private Logger logger = LoggerFactory.getLogger(AvaliaProposta.class);

    public StatusAvaliacaoProposta executar(Proposta novaProposta) { //2

        AvaliacaoPropostaRequest resultadoAvalicao =
                new AvaliacaoPropostaRequest(novaProposta); //3

        try{ //4
             resultadoAvalicao = integracoesAnaliseFinanceira
                     .avalia(new AvaliacaoPropostaRequest(novaProposta));

            return resultadoAvalicao.getResultadoSolicitacao().normaliza();

        }catch (FeignException e){ //5

            int httpStatusRequisicao = 422;

            if (e.status() == httpStatusRequisicao){ //6

                logger.error("Resultado consulta sistema legado:{}", e.contentUTF8());

                try { //7
                    resultadoAvalicao = new ObjectMapper().readValue(e.contentUTF8(),
                                    AvaliacaoPropostaRequest.class);
                } catch (JsonProcessingException jsonProcessingException) { //8
                    jsonProcessingException.printStackTrace();
                }

                return resultadoAvalicao.getResultadoSolicitacao().normaliza();
            }

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Houve um erro na comunicação com o servidor");
        }

    }

}
