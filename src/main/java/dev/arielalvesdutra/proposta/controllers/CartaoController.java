package dev.arielalvesdutra.proposta.controllers;

import dev.arielalvesdutra.proposta.controllers.dtos.CadastrarBiometriaRequestDTO;
import dev.arielalvesdutra.proposta.controllers.dtos.CadastrarAvisoViagemRequestDTO;
import dev.arielalvesdutra.proposta.entities.*;
import dev.arielalvesdutra.proposta.entities.enums.CarteiraTipo;
import dev.arielalvesdutra.proposta.services.*;
import dev.arielalvesdutra.proposta.services.dtos.AssociarCarteiraDTO;
import dev.arielalvesdutra.proposta.services.dtos.CadastrarAvisoViagemDTO;
import dev.arielalvesdutra.proposta.services.dtos.CadastrarBloqueioDTO;
import dev.arielalvesdutra.proposta.services.dtos.CadastrarSolicitacaoRecuperacaoSenhaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/api/cartoes")
@RestController
public class CartaoController {

    @Autowired
    private final CartaoService cartaoService;

    @Autowired
    private final BiometriaService biometriaService;

    @Autowired
    private final SolicitacaoRecuperacaoSenhaService solicitacaoRecuperacaoSenhaService;

    @Autowired
    private final AvisoViagemService avisoViagemService;

    @Autowired
    private final CarteiraService carteiraService;

    public CartaoController(
            CartaoService cartaoService,
            BiometriaService biometriaService,
            SolicitacaoRecuperacaoSenhaService solicitacaoRecuperacaoSenhaService,
            AvisoViagemService avisoViagemService,
            CarteiraService carteiraService) {

        this.cartaoService = cartaoService;
        this.biometriaService = biometriaService;
        this.solicitacaoRecuperacaoSenhaService = solicitacaoRecuperacaoSenhaService;
        this.avisoViagemService = avisoViagemService;
        this.carteiraService = carteiraService;
    }

    @PostMapping("/{cartaoId}/biometrias")
    public ResponseEntity<String> cadastrarBiometria(
            UriComponentsBuilder uriBuilder,
            @PathVariable String cartaoId,
            @Valid @RequestBody CadastrarBiometriaRequestDTO requestDTO){

        final Biometria biometriaCadastrada = biometriaService.cadastrar(cartaoId, requestDTO);

        final URI uri = uriBuilder.path("/api/cartoes/{cartaoId}/biometrias/{biometriaId}")
                .buildAndExpand(cartaoId, biometriaCadastrada.getId())
                .toUri();

        return ResponseEntity.created(uri).body(biometriaCadastrada.getId());
    }

    @PostMapping("/{cartaoId}/bloqueios")
    public ResponseEntity<String> bloquearCartao(
            UriComponentsBuilder uriBuilder,
            HttpServletRequest request,
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String cartaoId) {

        final var cadastrarBloqueioDTO =  new CadastrarBloqueioDTO()
                .setEmail(jwt.getClaim("email"))
                .setIp(request.getRemoteAddr())
                .setUserAgent(request.getHeader("User-Agent"));

        CartaoBloqueio bloqueioCadastrado = cartaoService.bloquear(cartaoId, cadastrarBloqueioDTO);

        final URI uri = uriBuilder.path("/api/cartoes/{cartaoId}/bloqueios/{bloqueioId}")
                .buildAndExpand(cartaoId, bloqueioCadastrado.getId())
                .toUri();

        return ResponseEntity.created(uri).body(bloqueioCadastrado.getId());
    }

    @PostMapping("/{cartaoId}/recuperacao-senha")
    public ResponseEntity<String> cadastrarSolicitacaoDeRecuperacaoDeSenha(
            UriComponentsBuilder uriBuilder,
            HttpServletRequest request,
            @PathVariable String cartaoId) {

        final var dto = new CadastrarSolicitacaoRecuperacaoSenhaDTO()
                .setIp(request.getRemoteAddr())
                .setUserAgent(request.getHeader("User-Agent"));

        SolicitacaoRecuperacaoSenha solicitacaoCadastrada = solicitacaoRecuperacaoSenhaService.cadastrar(cartaoId, dto);

        final URI uri = uriBuilder.path("/api/cartoes/{cartaoId}/recuperacao-senha/{solicitacaoId}")
                .buildAndExpand(cartaoId, solicitacaoCadastrada.getId())
                .toUri();

        return ResponseEntity.created(uri).body(solicitacaoCadastrada.getId());
    }

    @PostMapping("/{cartaoId}/avisos")
    public ResponseEntity<String> cadastrarAvisoDeViagem(
            UriComponentsBuilder uriBuilder,
            HttpServletRequest request,
            @RequestBody CadastrarAvisoViagemRequestDTO requestDTO,
            @PathVariable String cartaoId) {

        final var dto = new CadastrarAvisoViagemDTO()
                .setIp(request.getRemoteAddr())
                .setUserAgent(request.getHeader("User-Agent"))
                .setDestino(requestDTO.getDestino())
                .setTermino(requestDTO.getTermino());

        AvisoViagem avisoCadastrado = avisoViagemService.cadastrar(cartaoId, dto);

        final URI uri = uriBuilder.path("/api/cartoes/{cartaoId}/recuperacao-senha/{solicitacaoId}")
                .buildAndExpand(cartaoId, avisoCadastrado.getId())
                .toUri();

        return ResponseEntity.created(uri).body(avisoCadastrado.getId());
    }

    @PostMapping("/{cartaoId}/carteiras/paypal")
    public ResponseEntity<String> cadastrarCarteiraPaypal(
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String cartaoId) {

        final var dto = new AssociarCarteiraDTO()
                .setEmail(jwt.getClaim("email"))
                .setTipo(CarteiraTipo.PAYPAL);

        Carteira carteiraCadastrada = carteiraService.associa(cartaoId, dto);

        final URI uri = uriBuilder.path("/api/cartoes/{cartaoId}/carteiras/paypal")
                .buildAndExpand(cartaoId)
                .toUri();

        return ResponseEntity.created(uri).body(carteiraCadastrada.getId());
    }

    @PostMapping("/{cartaoId}/carteiras/samsung-play")
    public ResponseEntity<String> cadastrarCarteiraSamsungPlay(
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String cartaoId) {

        final var dto = new AssociarCarteiraDTO()
                .setEmail(jwt.getClaim("email"))
                .setTipo(CarteiraTipo.SAMSUNG_PLAY);

        Carteira carteiraCadastrada = carteiraService.associa(cartaoId, dto);

        final URI uri = uriBuilder.path("/api/cartoes/{cartaoId}/carteiras/samsung-play")
                .buildAndExpand(cartaoId)
                .toUri();

        return ResponseEntity.created(uri).body(carteiraCadastrada.getId());
    }
}
