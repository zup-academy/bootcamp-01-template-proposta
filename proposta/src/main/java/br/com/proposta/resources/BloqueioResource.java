package br.com.proposta.resources;

import br.com.proposta.entidades.Bloqueio;
import br.com.proposta.repositories.PropostaRepository;
import br.com.proposta.compartilhado.BloquearCartao;
import br.com.proposta.compartilhado.BuscarIPeUserAgentNaRequisicao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/bloqueios/{cartaoId}")
public class BloqueioResource {

    /* total de pontos = 6 */

    private final Logger logger = LoggerFactory.getLogger(Bloqueio.class);

    /* @complexidade - acoplamento contextual */
    private final BloquearCartao bloquearCartao;

    /* @complexidade - acoplamento contextual */
    private final BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao;

    /* @complexidade - acoplamento contextual */
    private final PropostaRepository propostaRepository;


    public BloqueioResource(BloquearCartao bloquearCartao, BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao,
                            PropostaRepository propostaRepository) {
        this.bloquearCartao = bloquearCartao;
        this.buscarIPeUserAgentNaRequisicao = buscarIPeUserAgentNaRequisicao;
        this.propostaRepository = propostaRepository;
    }



    @PostMapping
    public ResponseEntity<?> bloqueia(@PathVariable String cartaoId, UriComponentsBuilder uriComponentsBuilder,
                                    HttpServletRequest httpRequest){


        /* @complexidade - classe criada no projeto */
        var userAgentEInternetProtocol = buscarIPeUserAgentNaRequisicao
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        /* @complexidade - classe criada no projeto */
        var bloqueioResponse = bloquearCartao.bloquear(cartaoId, userAgentEInternetProtocol);

        /* @complexidade - classe criada no projeto */
        var bloqueio = new Bloqueio(userAgentEInternetProtocol, bloqueioResponse);

        logger.info("Bloqueio realizado com sucesso no cartão");

        return ResponseEntity
                .created(uriComponentsBuilder.path("/bloqueios/{propostaId}").buildAndExpand(bloqueio.getId()).toUri()).build();

    }
}
