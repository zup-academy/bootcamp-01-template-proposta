package br.com.proposta.resources;

import br.com.proposta.dtos.requests.AvisarViagemRequest;
import br.com.proposta.entidades.Aviso;
import br.com.proposta.repositories.AvisoRepository;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.compartilhado.BuscarIPeUserAgentNaRequisicao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/viagens")
public class AvisoResource {

    /* total de pontos = 7 */

    /* @complexidade - acoplamento contextual */
    private final AvisoRepository avisoRepository;

    /* @complexidade - acoplamento contextual */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - acoplamento contextual */
    private final BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao;


    private final Logger logger = LoggerFactory.getLogger(Aviso.class);


    public AvisoResource(AvisoRepository avisoRepository, IntegracaoApiCartoes integracaoApiCartoes,
                         BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao) {
        this.avisoRepository = avisoRepository;
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.buscarIPeUserAgentNaRequisicao = buscarIPeUserAgentNaRequisicao;
    }


    @PostMapping("/{numeroCartao}")
    public ResponseEntity<?> avisa(@PathVariable String numeroCartao, @RequestBody AvisarViagemRequest avisarViagemRequest,
                            HttpServletRequest httpRequest){

        /* @complexidade - utilizando duas classes criadas no projeto */
        var resposta = integracaoApiCartoes
                .avisarViagem(numeroCartao, avisarViagemRequest);

        /* @complexidade - utilizando classe criada no projeto */
        var userAgentEip = buscarIPeUserAgentNaRequisicao
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        /* @complexidade - if */
        if(resposta.getStatusCode() == HttpStatus.OK){

            /* @complexidade - instanciando classe criada no projeto */
            var novoAviso = new Aviso(numeroCartao, userAgentEip, resposta.getBody());

            avisoRepository.save(novoAviso);

            logger.info("Aviso devidamente registrado e pode ser identificado pelo número {}",
                    novoAviso.getId());

            return ResponseEntity.ok().build();

        }

        logger.info("Cartão de número {} não foi encontrado", numeroCartao);

        return ResponseEntity
                .notFound()
                .build();

    }
}
