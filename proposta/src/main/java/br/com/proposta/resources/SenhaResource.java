package br.com.proposta.resources;

import br.com.proposta.services.BuscarIPeUserAgentNaRequisicao;
import br.com.proposta.entidades.Senha;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.repositories.SenhaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/senhas/{numeroCartao}")
public class SenhaResource {

    /* total de pontos = 4 */

    /* @complexidade - acoplamento contextual */
    private final BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao;

    /* @complexidade - acoplamento contextual */
    private final SenhaRepository senhaRepository;

    /* @complexidade - acoplamento contextual */
    private final CartaoRepository cartaoRepository;


    public SenhaResource(BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao,
                         SenhaRepository senhaRepository, CartaoRepository cartaoRepository) {

        this.buscarIPeUserAgentNaRequisicao = buscarIPeUserAgentNaRequisicao;
        this.senhaRepository = senhaRepository;
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping
    public ResponseEntity<?> recuperaSenha(@PathVariable String numeroCartao,
                  HttpServletRequest httpRequest, UriComponentsBuilder uriComponentsBuilder){

        var cartao = cartaoRepository.findByNumero(numeroCartao);

        if(cartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        /* @complexidade - classe criada no projeto */
        var IpEuserAgent = buscarIPeUserAgentNaRequisicao
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        /* @complexidade - classe criada no projeto */
        var senha = new Senha(IpEuserAgent, cartao.get());

        senhaRepository.save(senha);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/senhas/").buildAndExpand().toUri()).build();

    }
}
