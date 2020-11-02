package br.com.proposta.resources;

import br.com.proposta.compartilhado.BuscarIPeUserAgentNaRequisicao;
import br.com.proposta.entidades.Senha;
import br.com.proposta.repositories.SenhaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/senhas/{cartaoId}")
public class SenhaResource {

    /* total de pontos = 4 */

    /* @complexidade - acoplamento contextual */
    private final BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao;

    /* @complexidade - acoplamento contextual */
    private final SenhaRepository senhaRepository;


    public SenhaResource(BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao,
                         SenhaRepository senhaRepository) {

        this.buscarIPeUserAgentNaRequisicao = buscarIPeUserAgentNaRequisicao;
        this.senhaRepository = senhaRepository;
    }

    @PostMapping
    public ResponseEntity<?> recuperaSenha(@PathVariable String cartaoId,
                  HttpServletRequest httpRequest, UriComponentsBuilder uriComponentsBuilder){

        /* @complexidade - classe criada no projeto */
        var IpEuserAgent = buscarIPeUserAgentNaRequisicao
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        /* @complexidade - classe criada no projeto */
        Senha senha = new Senha(IpEuserAgent, cartaoId);

        senhaRepository.save(senha);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/senhas/").buildAndExpand().toUri()).build();

    }
}
