package br.com.zup.proposta.controller;

import br.com.zup.proposta.annotations.InformacoesObrigatorias;
import br.com.zup.proposta.model.Bloqueio;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.service.BloqueioCartaoService;
import br.com.zup.proposta.utils.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
@Validated
public class BloquearCartaoController {

    private EntityManager entityManager;
    private BloqueioCartaoService bloqueioCartaoService;

    public BloquearCartaoController(EntityManager entityManager, BloqueioCartaoService bloqueioCartaoService) {
        this.entityManager = entityManager;
        this.bloqueioCartaoService = bloqueioCartaoService;
    }

    @PostMapping("/{cartaoID}/bloquear")
    @Transactional
    public ResponseEntity bloquearCartao(@PathVariable UUID cartaoID, @InformacoesObrigatorias HttpServletRequest request, UriComponentsBuilder builder){

        Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, cartaoID));

        if(cartaoProcurado.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(Arrays.asList("Cartão não encontrado")));
        }

        Bloqueio bloqueio = new Bloqueio(request.getRemoteAddr(), request.getHeader("User-Agent"));
        entityManager.persist(bloqueio);

        Cartao cartao = cartaoProcurado.get();

        bloqueioCartaoService.bloqueioCartao(cartao);

        cartao.incluirBloqueioDoCartao(bloqueio);
        entityManager.merge(cartao);

        URI enderecoConsulta = builder.path("/bloqueios/{id}").buildAndExpand(bloqueio.getId()).toUri();
        return ResponseEntity.created(enderecoConsulta).build();
    }
}
