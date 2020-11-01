package br.com.proposta.resources;

import br.com.proposta.entidades.Bloqueio;
import br.com.proposta.entidades.Cartao;
import br.com.proposta.repositories.CartaoRepository;
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


    /* total de pontos = 7 */

    private final Logger logger = LoggerFactory.getLogger(Bloqueio.class);

    /* @complexidade - acoplamento contextual */
    private final BloquearCartao bloquearCartao;

    /* @complexidade - acoplamento contextual */
    private final BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao;

    /* @complexidade - acoplamento contextual */
    private final CartaoRepository cartaoRepository;



    public BloqueioResource(BloquearCartao bloquearCartao, BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao,
                            CartaoRepository cartaoRepository) {
        this.bloquearCartao = bloquearCartao;
        this.buscarIPeUserAgentNaRequisicao = buscarIPeUserAgentNaRequisicao;
        this.cartaoRepository = cartaoRepository;
    }



    @PostMapping
    public ResponseEntity<?> bloqueia(@PathVariable String cartaoId, UriComponentsBuilder uriComponentsBuilder,
                                    HttpServletRequest httpRequest){

        Cartao cartao = cartaoRepository.findByNumero(cartaoId);

        /* @complexidade - classe criada no projeto */
        var userAgentEInternetProtocol = buscarIPeUserAgentNaRequisicao
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);


        /* @complexidade - classe criada no projeto */
        Bloqueio bloqueio = bloquearCartao.bloquear(cartaoId, userAgentEInternetProtocol);

        /* @complexidade - classe criada no projeto */
        var bloqueioResponse = bloquearCartao.avisarLegadoDoBloqueio(cartao);

        /* @complexidade - classe criada no projeto */
        bloqueio.atualizaStatusAposRespostaDoLegado(bloqueioResponse.getResultado());


        logger.info("Bloqueio realizado com sucesso no cartão");

        return ResponseEntity
                .created(uriComponentsBuilder.path("/bloqueios/{propostaId}").buildAndExpand(cartaoId).toUri()).build();

    }
}
