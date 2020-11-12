package br.com.zup.bootcamp.proposta.api.externalsystem;

import br.com.zup.bootcamp.proposta.api.dto.RequestAvisoDto;
import br.com.zup.bootcamp.proposta.api.dto.RequestCarteiraDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Scope(scopeName = "prototype")
@FeignClient(name = "SistemaCartao", url = "${cartao.host}")
public interface LegadoSistemaCartao {

    @GetMapping("/cartoes")
    ResponseLegadoCartaoDto consultaCartaoPorIdProposta(@RequestParam String idProposta);

    @PostMapping("/cartoes/{idCartao}/bloqueios")
    ResponseEntity<?> bloqueiaCartaoPorIdCartao(@PathVariable UUID idCartao, @RequestBody Map<String, String> params);

    @PostMapping("/cartoes/{idCartao}/avisos")
    ResponseEntity<?> solicitarAvisoDeViagem(@PathVariable UUID idCartao, @RequestBody RequestAvisoDto requestAvisoDto);

    @PostMapping("/cartoes/{idCartao}/carteiras")
    ResponseEntity<?> associarCarteira(@PathVariable UUID idCartao, @RequestBody RequestCarteiraDto requestCarteiraDto);
}
