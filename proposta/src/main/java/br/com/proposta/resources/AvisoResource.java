package br.com.proposta.resources;

import br.com.proposta.dtos.requests.AvisarViagemRequest;
import br.com.proposta.entidades.Aviso;
import br.com.proposta.repositories.AvisoRepository;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.services.BuscarIPeUserAgentNaRequisicao;
import br.com.proposta.repositories.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


@RestController
@RequestMapping("/api/viagens")
public class AvisoResource {

    /* total de pontos = 10 */

    /* @complexidade - acoplamento contextual */
    private final AvisoRepository avisoRepository;

    /* @complexidade - acoplamento contextual */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - acoplamento contextual */
    private final BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao;

    /* @complexidade - acoplamento contextual */
    private final CartaoRepository cartaoRepository;


    private final Logger logger = LoggerFactory.getLogger(Aviso.class);


    public AvisoResource(AvisoRepository avisoRepository, IntegracaoApiCartoes integracaoApiCartoes,
                         BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao, CartaoRepository cartaoRepository) {
        this.avisoRepository = avisoRepository;
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.buscarIPeUserAgentNaRequisicao = buscarIPeUserAgentNaRequisicao;
        this.cartaoRepository = cartaoRepository;
    }


    @PostMapping("/{numeroCartao}")
    public ResponseEntity<?> avisa(@PathVariable String numeroCartao, @RequestBody AvisarViagemRequest avisarViagemRequest,
                                   HttpServletRequest httpRequest){

        /* @complexidade - utilizando classe criada no projeto */
        var userAgentEip = buscarIPeUserAgentNaRequisicao
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        /* @complexidade (2) - utilizando duas classes criadas no projeto + if */
        var resposta = integracaoApiCartoes.avisarViagem(numeroCartao, avisarViagemRequest);
        if(resposta.getStatusCode() == HttpStatus.OK){

            /* @complexidade (3) - classe criada no projeto */
            var novoAviso = new Aviso(userAgentEip, Objects.requireNonNull(resposta.getBody()));
            novoAviso.associaCartao(cartaoRepository.findByNumero(numeroCartao).get());
            avisoRepository.save(novoAviso);

            logger.info("Aviso devidamente registrado e pode ser identificado pelo número {}", novoAviso.getId());

            return ResponseEntity.ok().build();
        }

        logger.info("O aviso de viagem não foi realizado com sucesso. A tentativa teve origem em - IP: {}",
                userAgentEip.get(0));

        return ResponseEntity.badRequest().build();

    }
}
