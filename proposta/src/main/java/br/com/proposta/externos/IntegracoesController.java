package br.com.proposta.externos;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import br.com.proposta.dto.DocumentoConsultaDTO;
import br.com.proposta.dto.DocumentoRetornoDTO;

@RestController
public class IntegracoesController {
	
	private AtomicInteger contDocumentos = new AtomicInteger();

	private Logger logger = LoggerFactory.getLogger(IntegracoesController.class);
	
	@PostMapping(value = "/api/solicitacao")
	public DocumentoRetornoDTO avaliaDocumento(@RequestBody DocumentoConsultaDTO request) {		
		int contAtual = contDocumentos.getAndIncrement();
		logger.debug("Rota de avaliacao de Documento{}",request.toString());
		if(contAtual % 2 != 0) {
			return new DocumentoRetornoDTO("","","COM_RESTRICAO","");
		}
		return new DocumentoRetornoDTO("","","SEM_RESTRICAO","");
	}

}