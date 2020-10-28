package br.com.zup.cartaoproposta.controllers;

import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.entities.proposta.PropostaNovoRequest;
import br.com.zup.cartaoproposta.repositories.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

/**
 * Contagem de carga intrínseca da classe: 4
 */

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    //1
    private PropostaRepository propostaRepository;

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    //1
    public ResponseEntity<String> criaProposta(@RequestBody @Valid PropostaNovoRequest novaProposta, UriComponentsBuilder uriComponentsBuilder) {

        Optional<Proposta> testeProposta = propostaRepository.findByDocumento(novaProposta.getDocumentoApenasDigitos());

        //1
        if (testeProposta.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Campo documento já cadastrado");
        }

        //1
        Proposta proposta = novaProposta.toModel();
        propostaRepository.save(proposta);

        URI link = uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(link).build();
    }
}
