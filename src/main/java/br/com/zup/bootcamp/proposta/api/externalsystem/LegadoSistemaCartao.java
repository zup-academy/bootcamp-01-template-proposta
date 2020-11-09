package br.com.zup.bootcamp.proposta.api.externalsystem;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "SistemaCartao", url = "${cartao.host}")
public interface LegadoSistemaCartao {

    @GetMapping("/cartoes")
    ResponseLegadoCartaoDto consultaCartaoPorIdProposta(@RequestParam String idProposta);

    @PostMapping("/cartoes/{idCartao}/bloqueios")
    ResponseEntity<?> bloqueiaCartaoPorIdCartao(@PathVariable UUID idCartao, @RequestBody Map<String, String> params);
}
