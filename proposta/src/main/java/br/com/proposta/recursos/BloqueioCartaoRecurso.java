package br.com.proposta.recursos;

import br.com.proposta.entidades.Bloqueio;
import br.com.proposta.repositorios.PropostaRepository;
import br.com.proposta.compartilhado.BloquearCartao;
import br.com.proposta.compartilhado.BuscarIPeUserAgentNaRequisicao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/bloqueios/{propostaId}")
public class BloqueioCartaoRecurso {

    /* total de pontos = 6 */

    private final Logger logger = LoggerFactory.getLogger(Bloqueio.class);

    /* @complexidade - classe criada no projeto */
    private final BloquearCartao bloquearCartao;

    /* @complexidade - classe criada no projeto */
    private final BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao;

    /* @complexidade - classe criada no projeto */
    private final PropostaRepository propostaRepository;


    public BloqueioCartaoRecurso(BloquearCartao bloquearCartao, BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao,
                                 PropostaRepository propostaRepository) {
        this.bloquearCartao = bloquearCartao;
        this.buscarIPeUserAgentNaRequisicao = buscarIPeUserAgentNaRequisicao;
        this.propostaRepository = propostaRepository;
    }



    @PostMapping
    public ResponseEntity<?> bloqueia(@PathVariable String propostaId, UriComponentsBuilder uriComponentsBuilder,
                                    HttpServletRequest httpRequest){


        /* @complexidade - classe criada no projeto */
        var userAgentEInternetProtocol = buscarIPeUserAgentNaRequisicao
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        /* @complexidade - classe criada no projeto */
        var bloqueioResponse = bloquearCartao.bloquear(propostaId, userAgentEInternetProtocol);

        /* @complexidade - classe criada no projeto */
        var bloqueio = new Bloqueio(userAgentEInternetProtocol, bloqueioResponse);

        logger.info("Bloqueio realizado com sucesso no cartão");

        return ResponseEntity
                .created(uriComponentsBuilder.path("/bloqueios/{propostaId}").buildAndExpand(bloqueio.getId()).toUri()).build();

    }
}
