package br.com.proposta.externos;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.proposta.dto.DocumentoConsultaDTO;
import br.com.proposta.dto.DocumentoRetornoDTO;

//Contagem de Pontos - TOTAL:2
//1 - DocumentoConsultaDTO
//1 - DocumentoRetornoDTO

@FeignClient(url = "${enderecos-externos.base-url}", name = "integracoes")
public interface Integracoes {

	@PostMapping("/api/solicitacao")
	public DocumentoRetornoDTO avalia(DocumentoConsultaDTO request);
	
	
}	