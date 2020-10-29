package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.AssociaCarteiraRequest;
import br.com.proposta.models.CarteiraPaypal;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.repositories.CarteiraPaypalRepository;
import br.com.proposta.repositories.CarteiraSamsungRepository;
import br.com.proposta.services.IntegracaoCartaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AssociaCarteiraController {


    private IntegracaoCartaoService integracaoCartaoService;

    private CartaoRepository cartaoRepository;

    private CarteiraPaypalRepository carteiraPaypalRepository;

    private CarteiraSamsungRepository carteiraSamsungRepository;


    public AssociaCarteiraController(IntegracaoCartaoService integracaoCartaoService, CartaoRepository cartaoRepository,
                                     CarteiraPaypalRepository carteiraPaypalRepository, CarteiraSamsungRepository carteiraSamsungRepository) {
        this.integracaoCartaoService = integracaoCartaoService;
        this.cartaoRepository = cartaoRepository;
        this.carteiraPaypalRepository = carteiraPaypalRepository;
        this.carteiraSamsungRepository = carteiraSamsungRepository;
    }

    @PostMapping("/{cartaoId}")
    public ResponseEntity<?> associa(@PathVariable String cartaoId, @RequestBody AssociaCarteiraRequest associaCarteiraRequest){

        integracaoCartaoService.associarCarteira(cartaoId, associaCarteiraRequest);


        /*controller incompleto -> continua....*/

        return ResponseEntity.ok().build();

    }
}
