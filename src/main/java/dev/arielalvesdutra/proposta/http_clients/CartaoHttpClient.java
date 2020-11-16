package dev.arielalvesdutra.proposta.http_clients;

import dev.arielalvesdutra.proposta.entities.enums.CarteiraTipo;
import dev.arielalvesdutra.proposta.http_clients.dtos.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Cliente HTTP para o Serviço de Cartões.
 */
@Validated
@FeignClient(value = "cartao", url = "${cartao.url}")
public interface CartaoHttpClient {

    /**
     * Busca cartão pelo ID da proposta.
     *
     * @param idProposta
     *
     * @return
     */
    @GetMapping("/api/cartoes")
    CartaoRetornoDTO buscaCartao(@RequestParam String idProposta);

    /**
     * Envia requisiçao de bloqueio de cartão.
     *
     * @param cartaoLegadoId ID do Cartão no legado como Path Param.
     * @param requestBody Corpo da requisição.
     *
     * @return
     */
    @PostMapping("/api/cartoes/{cartaoLegadoId}/bloqueios")
    ResultadoBloqueioDTO bloqueiaCartao(
            @PathVariable String cartaoLegadoId,
            @RequestBody SolicitacaoBloqueioDTO requestBody);

    /**
     * Envia aviso de viagem.
     *
     * @param cartaoLegadoId ID do Cartão no legado como Path Param.
     * @param requestBody Corpo da requisição.
     *
     * @return
     */
    @PostMapping("/api/cartoes/{cartaoLegadoId}/avisos")
    ResultadoAvisoViagemDTO notificaAvisoDeViagem(
            @PathVariable String cartaoLegadoId,
            @RequestBody NotificacaoAvisoViagemDTO requestBody);

    /**
     * Envia requisição para cadastrar carteira ao cartão.
     *
     * Tipos de carteira: {@link CarteiraTipo}
     *
     * @param cartaolegadoId ID do Cartão no legado como Path Param.
     * @param requestBody Corpo da requisição.
     *
     * @return
     */
    @PostMapping("/api/cartoes/{cartaolegadoId}/carteiras")
    ResultadoAssociacaoCarteiraDTO associaCarteira(
            @PathVariable  String cartaolegadoId,
            @RequestBody SolicitacaoAssociacaoCarteiraDTO requestBody);
}
