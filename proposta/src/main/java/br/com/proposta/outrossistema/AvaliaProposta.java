package br.com.proposta.outrossistema;

import br.com.proposta.dtos.requests.SolicitacaoAnalise;
import br.com.proposta.dtos.responses.ResultadoAnalise;
import br.com.proposta.models.Enums.RespostaStatusAvaliacao;
import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.models.Proposta;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AvaliaProposta {

    @Autowired
    private Integracoes integracoes;

    public StatusAvaliacaoProposta retornarAvaliacao(Proposta proposta){

        String resultadoAnalise =
                    integracoes.avaliaproposta(new SolicitacaoAnalise(proposta));

        ResultadoAnalise resultadoAnaliseJson = new Gson().fromJson(resultadoAnalise, ResultadoAnalise.class);

        return resultadoAnaliseJson.retornaStatus();

    }
}
