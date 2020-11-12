package br.com.proposta.resources;

import br.com.proposta.entidades.Bloqueio;
import br.com.proposta.entidades.Cartao;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.services.BloquearCartao;
import br.com.proposta.services.BuscarIPeUserAgentNaRequisicao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/bloqueios/{numeroCartao}")
public class BloqueioResource {


    /* total de pontos = 8 */


    /* @complexidade (1) - acoplamento contextual */
    private final BloquearCartao bloquearCartao;

    /* @complexidade (1) - acoplamento contextual */
    private final BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao;

    /* @complexidade (1) - acoplamento contextual */
    private final CartaoRepository cartaoRepository;

    private final Logger logger = LoggerFactory.getLogger(Bloqueio.class);


    public BloqueioResource(BloquearCartao bloquearCartao, BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao,
                            CartaoRepository cartaoRepository) {
        this.bloquearCartao = bloquearCartao;
        this.buscarIPeUserAgentNaRequisicao = buscarIPeUserAgentNaRequisicao;
        this.cartaoRepository = cartaoRepository;
    }



    @PostMapping
    public ResponseEntity<?> bloqueia(@PathVariable String numeroCartao, UriComponentsBuilder uriComponentsBuilder,
                                    HttpServletRequest httpRequest){

        /* @complexidade (2) - classe criada no projeto + if */
        var cartao = cartaoRepository.findByNumero(numeroCartao);
        if(cartao.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        /* @complexidade (1) - classe criada no projeto */
        var userAgentEInternetProtocol = buscarIPeUserAgentNaRequisicao
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        /* @complexidade (2) - classe criada no projeto */
        bloquearCartao.bloquear(numeroCartao, userAgentEInternetProtocol);
        bloquearCartao.avisarLegadoDoBloqueio(cartao.get());


        logger.info("Bloqueio realizado do cartao de {} realizado com sucesso", cartao.get().getTitular());

        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/bloqueios/{numeroCartao}").buildAndExpand(numeroCartao).toUri()).build();

    }
}
