package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.AvisoViagemRequest;
import br.com.proposta.dtos.responses.AvisoViagemResponse;
import br.com.proposta.models.Aviso;
import br.com.proposta.models.Cartao;
import br.com.proposta.models.Enums.StatusAviso;
import br.com.proposta.repositories.AvisoRepository;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.services.IntegracaoCartaoService;
import br.com.proposta.services.UserAgentEInternetProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/viagens")
public class AvisoViagemController {


    @Autowired
    private AvisoRepository avisoRepository;


    @Autowired
    private IntegracaoCartaoService integracaoCartaoService;

    @Autowired
    private UserAgentEInternetProtocolService userAgentEInternetProtocolService;



    @PostMapping("/{idCartao}")
    public ResponseEntity<?> avisa(@PathVariable String idCartao, @RequestBody AvisoViagemRequest avisoViagemRequest,
                            HttpServletRequest httpRequest){

        AvisoViagemResponse resposta =
                integracaoCartaoService.avisarViagem(idCartao, avisoViagemRequest).getBody();

        List<String> userAgentEip = userAgentEInternetProtocolService
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        Aviso aviso = new Aviso(idCartao, userAgentEip.get(0),
                userAgentEip.get(1), StatusAviso.valueOf(resposta.getResultado()));

        avisoRepository.save(aviso);

        return ResponseEntity.ok().build();

    }

}
