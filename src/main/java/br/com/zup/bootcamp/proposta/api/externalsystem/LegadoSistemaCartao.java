package br.com.zup.bootcamp.proposta.api.externalsystem;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SistemaCartao", url = "${cartao.host}" )
public interface LegadoSistemaCartao {

    @GetMapping("/cartoes")
    ResponseLegadoCartaoDto consultaCartaoPorIdProposta(@RequestParam String idProposta);
}
