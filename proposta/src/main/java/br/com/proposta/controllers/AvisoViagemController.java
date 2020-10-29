package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.AvisoViagemRequest;
import br.com.proposta.dtos.responses.AvisoViagemResponse;
import br.com.proposta.repositories.ViagemRepository;
import br.com.proposta.services.IntegracaoCartaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viagens")
public class AvisoViagemController {


    private ViagemRepository viagemRepository;

    private IntegracaoCartaoService integracaoCartaoService;


    public AvisoViagemController(ViagemRepository viagemRepository, IntegracaoCartaoService integracaoCartaoService) {
        this.viagemRepository = viagemRepository;
        this.integracaoCartaoService = integracaoCartaoService;
    }


    @PostMapping("/{idCartao}")
    public ResponseEntity<?> avisa(@PathVariable String idCartao, @RequestBody AvisoViagemRequest avisoViagemRequest){

        ResponseEntity<AvisoViagemResponse> avisoViagemResposta =
                integracaoCartaoService.avisarViagem(idCartao, avisoViagemRequest);




        return ResponseEntity.ok().build();

    }
}
