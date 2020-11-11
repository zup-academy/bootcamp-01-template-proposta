package br.com.zup.cartaoproposta.controllers;

import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitanteRetorno;
import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.entities.proposta.PropostaNovoRequest;
import br.com.zup.cartaoproposta.entities.proposta.PropostaRetorno;
import br.com.zup.cartaoproposta.repositories.PropostaRepository;
import br.com.zup.cartaoproposta.services.analisesolicitante.TratamentoRetorno;
import br.com.zup.cartaoproposta.util.MetricasProposta;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

/**
 * Contagem de carga intrínseca da classe: 9
 */

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    //1
    private PropostaRepository propostaRepository;

    @Autowired
    //1
    private TratamentoRetorno tratamentoRetorno;

    //1
    private MetricasProposta metricas;

    public PropostaController(PropostaRepository propostaRepository, MeterRegistry meterRegistry) {
        this.propostaRepository = propostaRepository;
        metricas = new MetricasProposta(meterRegistry);
        metricas.criarCadastroPropostaContador();
    }

    @PostMapping
    @Transactional
    //1
    public ResponseEntity<String> cadastroProposta(@RequestBody @Valid PropostaNovoRequest novaProposta, UriComponentsBuilder uriComponentsBuilder) {

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

        metricas.incrementarCadastroPropostaContador();

        URI link = uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(link).build();
    }

    @GetMapping("/{id}")
    //1
    public ResponseEntity<PropostaRetorno> dadosProposta(@PathVariable("id") String idProposta) {

        Optional<Proposta> testeProposta = propostaRepository.findById(idProposta);

        //1
        if (testeProposta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PropostaRetorno propostaRetorno = new PropostaRetorno(testeProposta.get());

        return ResponseEntity.ok(propostaRetorno);
    }
}
