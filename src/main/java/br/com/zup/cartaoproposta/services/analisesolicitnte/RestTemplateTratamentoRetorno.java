package br.com.zup.cartaoproposta.services.analisesolicitnte;

import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitante;
import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitanteRetorno;
import br.com.zup.cartaoproposta.entities.analisesolicitante.ResultadoSolicitacao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

/**
 * Contagem de carga intrínseca da classe: 5
 */

@Primary
@Component
public class RestTemplateTratamentoRetorno implements TratamentoRetorno {

    @Value("${url.analise-cartoes}")
    private String url;

    @Override
    public AnaliseSolicitanteRetorno analiseSolicitante(String documentoSolicitante, String nomeSolicitante, String idProposta) {
        RestTemplate restTemplate = new RestTemplate();

        //1
        AnaliseSolicitante analiseSolicitante = new AnaliseSolicitante(documentoSolicitante, nomeSolicitante, idProposta);

        //2
        try{
            return restTemplate.postForObject(url+"/solicitacao", analiseSolicitante, AnaliseSolicitanteRetorno.class);
        } catch (HttpClientErrorException e) {
            String resultadoEsperadoMensagem = String.format(
                    "{" +
                            "\"documento\":\"%s\"," +
                            "\"nome\":\"%s\"," +
                            "\"resultadoSolicitacao\":\"COM_RESTRICAO\"," +
                            "\"idProposta\":\"%s\"" +
                            "}",
                    analiseSolicitante.getDocumento(),
                    analiseSolicitante.getNome(),
                    analiseSolicitante.getIdProposta()
            );

            int resultadoEsperadoStatusCode = 422;

            //1
            if (e.getStatusCode().value() == resultadoEsperadoStatusCode
                    && Objects.requireNonNull(e.getMessage()).contains(resultadoEsperadoMensagem)) {
                //1
                return new AnaliseSolicitanteRetorno(analiseSolicitante.getDocumento(), analiseSolicitante.getNome(), ResultadoSolicitacao.COM_RESTRICAO, analiseSolicitante.getIdProposta());
            }
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro na comunicação com validação do solicitante");
    }
}
