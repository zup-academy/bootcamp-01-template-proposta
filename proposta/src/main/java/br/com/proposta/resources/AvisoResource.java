package br.com.proposta.resources;

import br.com.proposta.dtos.requests.AvisarViagemRequest;
import br.com.proposta.entidades.Aviso;
import br.com.proposta.repositories.AvisoRepository;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.compartilhado.BuscarIPeUserAgentNaRequisicao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/viagens")
public class AvisoResource {


    /* total de pontos = 6 */

    /* @complexidade - acoplamento contextual */
    private final AvisoRepository avisoRepository;

    /* @complexidade - acoplamento contextual */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - acoplamento contextual */
    private final BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao;



    public AvisoResource(AvisoRepository avisoRepository, IntegracaoApiCartoes integracaoApiCartoes,
                         BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao) {
        this.avisoRepository = avisoRepository;
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.buscarIPeUserAgentNaRequisicao = buscarIPeUserAgentNaRequisicao;
    }


    @PostMapping("/{idCartao}")
    public ResponseEntity<?> avisa(@PathVariable String idCartao, @RequestBody AvisarViagemRequest avisarViagemRequest,
                            HttpServletRequest httpRequest){

        /* @complexidade - utilizando duas classes criadas no projeto */
        var resposta = integracaoApiCartoes.avisarViagem(idCartao, avisarViagemRequest);

        /* @complexidade - utilizando classe criada no projeto */
        var userAgentEip = buscarIPeUserAgentNaRequisicao.recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        /* @complexidade - if */
        if(resposta.getStatusCode() == HttpStatus.OK){

            /* @complexidade - instanciando classe criada no projeto */
            var novoAviso = new Aviso(idCartao, userAgentEip, resposta.getBody());

            avisoRepository.save(novoAviso);

            return ResponseEntity
                    .ok()
                    .build();

        }

        return ResponseEntity
                .notFound()
                .build();

    }
}
