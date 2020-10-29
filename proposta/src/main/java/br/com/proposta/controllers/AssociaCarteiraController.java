package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.AssociaCarteiraRequest;
import br.com.proposta.repositories.CarteiraRepository;
import br.com.proposta.services.IntegracaoCartaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AssociaCarteiraController {


    private IntegracaoCartaoService integracaoCartaoService;

    private CarteiraRepository carteiraRepository;


    public AssociaCarteiraController(IntegracaoCartaoService integracaoCartaoService,
                                     CarteiraRepository carteiraRepository) {
        this.integracaoCartaoService = integracaoCartaoService;
        this.carteiraRepository = carteiraRepository;
    }

    @PostMapping("/{cartaoId}")
    public ResponseEntity<?> associa(@PathVariable String cartaoId, @RequestBody AssociaCarteiraRequest associaCarteiraRequest){

        integracaoCartaoService.associarCarteira(cartaoId, associaCarteiraRequest);


        /*controller incompleto -> continua....*/

        return ResponseEntity.ok().build();

    }
}
