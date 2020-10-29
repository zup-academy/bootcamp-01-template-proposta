package br.com.zup.cartaoproposta.services;

import br.com.zup.cartaoproposta.clienteswebservices.AnaliseCartoesClient;
import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitante;
import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitanteRetorno;
import br.com.zup.cartaoproposta.entities.analisesolicitante.ResultadoSolicitacao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Contagem de carga intrínseca da classe: 7
 */

@Component
public class FeignTratamentoRetorno {

    @Autowired
    //1
    AnaliseCartoesClient analiseCartoesClient;

    public FeignTratamentoRetorno(AnaliseCartoesClient analiseCartoesClient) {
        this.analiseCartoesClient = analiseCartoesClient;
    }

    //1
    public AnaliseSolicitanteRetorno analiseSolicitante(String documentoSolicitante, String nomeSolicitante, String idProposta) {
        //1
        AnaliseSolicitante analiseSolicitante = new AnaliseSolicitante(documentoSolicitante, nomeSolicitante, idProposta);

        //2
        try{
            return analiseCartoesClient.solicitacaoAnaliseResource(analiseSolicitante);
        } catch (FeignException e) {
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
            if (e.status() == resultadoEsperadoStatusCode
                    && e.getMessage().contains(resultadoEsperadoMensagem)) {
                //1
                return new AnaliseSolicitanteRetorno(analiseSolicitante.getDocumento(), analiseSolicitante.getNome(), ResultadoSolicitacao.COM_RESTRICAO, analiseSolicitante.getIdProposta());
            }
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro na comunicação com validação do solicitante");
    }
}
