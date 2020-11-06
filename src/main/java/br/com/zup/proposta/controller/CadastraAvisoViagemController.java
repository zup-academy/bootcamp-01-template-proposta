package br.com.zup.proposta.controller;

import br.com.zup.proposta.annotations.InformacoesObrigatorias;
import br.com.zup.proposta.dto.request.AvisoViagemRequest;
import br.com.zup.proposta.integration.IntegracaoCartao;
import br.com.zup.proposta.model.AvisoViagem;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.utils.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
@Validated
public class CadastraAvisoViagemController {

    private EntityManager entityManager;

    private IntegracaoCartao integracaoCartao;

    private Logger logger = LoggerFactory.getLogger(AvisoViagem.class);

    public CadastraAvisoViagemController(EntityManager entityManager, IntegracaoCartao integracaoCartao) {
        this.entityManager = entityManager;
        this.integracaoCartao = integracaoCartao;
    }

    @PostMapping("/{cartaoID}/aviso_viagem")
    @Transactional
    public ResponseEntity cadastrarAvisoViagem(@PathVariable UUID cartaoID, @RequestBody @Valid AvisoViagemRequest avisoViagemRequest, @InformacoesObrigatorias HttpServletRequest request,
                                               UriComponentsBuilder builder) {

        Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, cartaoID));

        if(cartaoProcurado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(Arrays.asList("Cartão não encontrado")));
        }

        integracaoCartao.enviarAvisoDeViagem(cartaoID, avisoViagemRequest);

        AvisoViagem avisoViagem = avisoViagemRequest.toModel(request);
        entityManager.persist(avisoViagem);

        Cartao cartao = cartaoProcurado.get();
        cartao.incluirAvisoDeViagem(avisoViagem);
        entityManager.merge(cartao);

        URI enderecoConsulta = builder.path("/avisos/{id}").buildAndExpand(avisoViagem.getId()).toUri();
        return ResponseEntity.created(enderecoConsulta).build();
    }

}
