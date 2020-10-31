package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.AvisoViagemRequest;
import br.com.proposta.models.Aviso;
import br.com.proposta.models.Enums.StatusAviso;
import br.com.proposta.repositories.AvisoRepository;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.services.UserAgentEInternetProtocolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/viagens")
public class AvisoViagemController {

    /* total de pontos = 6 */

    /* @complexidade - classe criada no projeto */
    private final AvisoRepository avisoRepository;

    /* @complexidade - classe criada no projeto */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - classe criada no projeto */
    private final UserAgentEInternetProtocolService userAgentEInternetProtocolService;


    public AvisoViagemController(AvisoRepository avisoRepository, IntegracaoApiCartoes integracaoApiCartoes,
                                 UserAgentEInternetProtocolService userAgentEInternetProtocolService) {
        this.avisoRepository = avisoRepository;
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.userAgentEInternetProtocolService = userAgentEInternetProtocolService;
    }

    @PostMapping("/{idCartao}")
    public ResponseEntity<?> avisa(@PathVariable String idCartao, @RequestBody AvisoViagemRequest avisoViagemRequest,
                            HttpServletRequest httpRequest){

        /* @complexidade - utilizando duas classes criada no projeto */
        var resposta = integracaoApiCartoes.avisarViagem(idCartao, avisoViagemRequest).getBody();

        /* @complexidade - utilizando classe criada no projeto */
        var userAgentEip = userAgentEInternetProtocolService.recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        /* @complexidade - instanciando classe criada no projeto */
        var novoAviso = new Aviso(idCartao, userAgentEip, resposta);

        avisoRepository.save(novoAviso);

        return ResponseEntity
                .ok()
                .build();

    }
}
