package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.AssociaCarteiraRequest;
import br.com.proposta.dtos.responses.AssociaCarteiraResponse;
import br.com.proposta.models.Carteira;
import br.com.proposta.models.Enums.StatusCarteira;
import br.com.proposta.repositories.CarteiraRepository;
import br.com.proposta.services.IntegracaoCartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AssociaCarteiraController {

    @Autowired
    private IntegracaoCartaoService integracaoCartaoService;

    @Autowired
    private CarteiraRepository carteiraRepository;


    @PostMapping("/{cartaoId}")
    public ResponseEntity<?> associa(@PathVariable String cartaoId,
                                     @RequestBody AssociaCarteiraRequest associaCarteiraRequest){

        ResponseEntity<AssociaCarteiraResponse> response =
                integracaoCartaoService.associarCarteira(cartaoId, associaCarteiraRequest);

        StatusCarteira status = StatusCarteira.valueOf(response.getBody().getResultado());

        Carteira carteira =
                new Carteira(associaCarteiraRequest.getCarteira(), cartaoId, status);

        carteiraRepository.save(carteira);

        return ResponseEntity.ok().build();

    }
}
