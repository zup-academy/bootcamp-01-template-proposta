package br.com.zup.cartaoproposta.controllers;

import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitanteRetorno;
import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.entities.proposta.PropostaNovoRequest;
import br.com.zup.cartaoproposta.repositories.PropostaRepository;
import br.com.zup.cartaoproposta.services.analisesolicitnte.TratamentoRetorno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

/**
 * Contagem de carga intrínseca da classe: 6
 */

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    //1
    private PropostaRepository propostaRepository;

    @Autowired
    //1
    private TratamentoRetorno tratamentoRetorno;

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    @Transactional
    //1
    public ResponseEntity<String> criaProposta(@RequestBody @Valid PropostaNovoRequest novaProposta, UriComponentsBuilder uriComponentsBuilder) {

        //1
        Optional<Proposta> testeProposta = propostaRepository.findByDocumento(novaProposta.getDocumentoApenasDigitos());

        //1
        if (testeProposta.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Campo documento já cadastrado");
        }

        Proposta proposta = novaProposta.toModel();
        propostaRepository.save(proposta);

        //1
        AnaliseSolicitanteRetorno retorno = tratamentoRetorno.analiseSolicitante(proposta.getDocumento(), proposta.getNome(), proposta.getId());

        proposta.defineStatusProposta(retorno.getResultadoSolicitacao());
        propostaRepository.save(proposta);

        URI link = uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(link).build();
    }
}
