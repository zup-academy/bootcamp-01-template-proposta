package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.request.NovaPropostaRequest;
import br.com.cartao.proposta.domain.response.NovaPropostaResponseDto;
import br.com.cartao.proposta.handler.ErroNegocioException;
import br.com.cartao.proposta.repository.PropostaRepository;
import br.com.cartao.proposta.service.EncodeValor;
import br.com.cartao.proposta.service.NovaPropostaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 7
 */

@RestController
@RequestMapping("/v1/propostas")
public class NovaPropostaController {

    private static Logger logger = LoggerFactory.getLogger(NovaPropostaController.class);
    // +1
    private final PropostaRepository propostaRepository;
    // +1
    private final NovaPropostaService novaPropostaService;

    public NovaPropostaController(PropostaRepository propostaRepository, NovaPropostaService novaPropostaService) {
        this.propostaRepository = propostaRepository;
        this.novaPropostaService = novaPropostaService;
    }

    @PostMapping
    @Transactional
    // +1
    public ResponseEntity<?> criaNovaProposta(@RequestBody @Valid NovaPropostaRequest novaPropostaRequest,
                                              UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {

        String emailUsuario = getEmailUsuarioSolicitante();
        logger.info("Usuario com email: {} , solicitou uma nova proposta.", emailUsuario);

        // +1
        propostaRepository.findAll().forEach(proposta -> {
            // +1
            if(novaPropostaRequest.equals(EncodeValor.decode(proposta.getDocumento()))){
                // +1
                throw new ErroNegocioException(HttpStatus.UNPROCESSABLE_ENTITY, "CPF ou CNPJ já em uso");
            }
        });

        logger.info("Requisição recebida: {}", novaPropostaRequest);

        // +1
        NovaPropostaResponseDto novaPropostaResponseDto = novaPropostaService.criaNovaProposta(novaPropostaRequest);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/v1/propostas/{id}").buildAndExpand(novaPropostaResponseDto.getId()).toUri())
                .build();
    }

    private String getEmailUsuarioSolicitante() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        String emailUsuario = (String) principal.getClaims().get("email");
        return emailUsuario;
    }
}
