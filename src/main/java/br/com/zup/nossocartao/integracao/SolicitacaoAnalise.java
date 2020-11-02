package br.com.zup.nossocartao.integracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "solicitacaoAnaliseResource", url = "${api.solicitacao.analise}")
public interface SolicitacaoAnalise {

	@RequestMapping(method = RequestMethod.POST, value = "/solicitacao")
	SolicitacaoAnaliseResponse resultadoAnalise(SolicitacaoAnaliseRequest request);

}
