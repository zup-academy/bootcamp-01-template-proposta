package br.com.zup.proposta.controller;

import br.com.zup.proposta.annotations.InformacoesObrigatorias;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.RecuperaSenha;
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
public class RecuperarSenhaController {

    private EntityManager entityManager;

    public RecuperarSenhaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/{cartaoID}/recuperar_senha")
    @Transactional
    public ResponseEntity recuperarSenha(@PathVariable UUID cartaoID, @InformacoesObrigatorias HttpServletRequest request, UriComponentsBuilder builder){

        Optional<Cartao> cartao = Optional.ofNullable(entityManager.find(Cartao.class, cartaoID));

        if(cartao.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(Arrays.asList("Cartão não encontrado")));
        }

        RecuperaSenha recuperarSenha = new RecuperaSenha(request.getRemoteAddr(), request.getHeader("User-Agent"), cartao.get());
        entityManager.persist(recuperarSenha);

        URI enderecoConsulta = builder.path("/recuperar_senha/{id}").buildAndExpand(recuperarSenha.getId()).toUri();
        return ResponseEntity.created(enderecoConsulta).build();
    }
}