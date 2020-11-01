package com.proposta.feign;

import com.proposta.feign.response.ResultadoAnaliseResponse;
import com.proposta.feign.request.SolicitacaoAnaliseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(url = "http://localhost:9999", name = "AnalisePropostaCliente")
public interface AnalisePropostaCliente {

    @PostMapping("/api/solicitacao")
    @ResponseBody
    ResultadoAnaliseResponse solicitarAnalise(@RequestBody SolicitacaoAnaliseRequest request);
}
