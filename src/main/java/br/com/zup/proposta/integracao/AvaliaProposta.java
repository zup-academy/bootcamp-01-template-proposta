package br.com.zup.proposta.integracao;

import br.com.zup.proposta.novaproposta.Proposta;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Service
@Validated
public class AvaliaProposta {
    @Autowired
    private Integracoes integracoes;

    private Logger logger = LoggerFactory.getLogger(Proposta.class);

    public StatusAvaliacaoProposta executa(@NotNull @Validated Proposta proposta) {
        String resultadoAvaliacao = integracoes.avalia(new DocumentoRequest(proposta));

        ResultadoAnaliseResponse resultadoAnaliseResponseJson = new Gson().fromJson(resultadoAvaliacao,
                ResultadoAnaliseResponse.class);

        logger.info("Proposta={} Status={} status de avaliação de proposta retornado com sucesso!",
                resultadoAnaliseResponseJson.getNome(), resultadoAnaliseResponseJson.getResultadoSolicitacao());
        return resultadoAnaliseResponseJson.retornaStatus();
    }
}
