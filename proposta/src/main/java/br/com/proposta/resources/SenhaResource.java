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


    private final BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao;

    private final SenhaRepository senhaRepository;


    public SenhaResource(BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao,
                         SenhaRepository senhaRepository) {

        this.buscarIPeUserAgentNaRequisicao = buscarIPeUserAgentNaRequisicao;
        this.senhaRepository = senhaRepository;
    }

    @PostMapping
    public ResponseEntity<?> recuperaSenha(@PathVariable String cartaoId,
                  HttpServletRequest httpRequest, UriComponentsBuilder uriComponentsBuilder){

        var IpEuserAgent = buscarIPeUserAgentNaRequisicao
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        Senha senha = new Senha(IpEuserAgent, cartaoId);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/senhas/").buildAndExpand().toUri()).build();

    }
}
