package br.com.zup.cartaoproposta.services.analisesolicitnte;

import br.com.zup.cartaoproposta.clienteswebservices.AnaliseCartoesClient;
import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitante;
import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitanteRetorno;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Contagem de carga intrínseca da classe: 8
 */

@Component
public class FeignTratamentoRetorno implements TratamentoRetorno {

    @Autowired
    //1
    private AnaliseCartoesClient analiseCartoesClient;

    @Override
    //1
    public AnaliseSolicitanteRetorno analiseSolicitante (String documentoSolicitante, String nomeSolicitante, String idProposta) {
        //1
        AnaliseSolicitante analiseSolicitante = new AnaliseSolicitante(documentoSolicitante, nomeSolicitante, idProposta);

        //2
        try{
            return analiseCartoesClient.solicitacaoAnaliseResource(analiseSolicitante);
        } catch (FeignException e) {
            int resultadoEsperadoStatusCode = 422;
            //1
            if (e.status() == resultadoEsperadoStatusCode) {
                //2
                try {
                    return new ObjectMapper().readValue(e.contentUTF8(), AnaliseSolicitanteRetorno.class);
                } catch (JsonProcessingException jsonProcessingException) {
                    jsonProcessingException.printStackTrace();
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro na comunicação com validação do solicitante");
    }

}
