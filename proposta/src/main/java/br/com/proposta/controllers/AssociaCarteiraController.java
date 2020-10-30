package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.AssociaCarteiraRequest;
import br.com.proposta.dtos.responses.AssociaCarteiraResponse;
import br.com.proposta.models.Cartao;
import br.com.proposta.models.Carteira;
import br.com.proposta.models.Enums.StatusCarteira;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.repositories.CarteiraRepository;
import br.com.proposta.services.IntegracaoCartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/carteiras")
public class AssociaCarteiraController {

    @Autowired
    private IntegracaoCartaoService integracaoCartaoService;

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private CartaoRepository cartaoRepository;


    @PostMapping("/{cartaoId}")
    public ResponseEntity<?> associa(@PathVariable String cartaoId,
                                     @RequestBody AssociaCarteiraRequest associaCarteiraRequest){

        Optional<Cartao> cartao = cartaoRepository.findById(cartaoId);

        ResponseEntity<AssociaCarteiraResponse> response =
                integracaoCartaoService.associarCarteira(cartaoId, associaCarteiraRequest);

        StatusCarteira status = StatusCarteira.valueOf(response.getBody().getResultado());

        Carteira carteira =
                new Carteira(associaCarteiraRequest.getCarteira(), status, cartao.get());

        carteiraRepository.save(carteira);

        return ResponseEntity.ok().build();

    }
}
