package br.com.proposta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.proposta.component.ExecutorTransacao;
import br.com.proposta.dto.AvisoViagemConsultaDTO;
import br.com.proposta.dto.AvisoViagemDTO;
import br.com.proposta.externos.Cartoes;
import br.com.proposta.model.AvisoViagem;
import br.com.proposta.model.Cartao;

//Contagem de Pontos - TOTAL:6
//1 - ExecutorTransacao
//1 - AvisoViagemConsultaDTO
//1 - AvisoViagemDTO
//1 - Cartoes
//1 - AvisoViagem
//1 - Cartao

@Service
public class AvisoViagemServices {

	@Autowired
	private Cartoes cartoes;
	
	@Autowired
	private ExecutorTransacao executorTransacao;
	
	private Logger logger = LoggerFactory.getLogger(AvisoViagemServices.class);
	
	public void enviarAvisoViagem(Cartao cartao, AvisoViagemDTO avisoViagemDTO, String userAgent, String ip) {
		ResponseEntity<String> respostaAvisoViagem = cartoes.avisoViagem(cartao.getNumero(), new AvisoViagemConsultaDTO(avisoViagemDTO.getDestino(), avisoViagemDTO.getTerminoViagem()));
		logger.info("Retorno Sistema de Cartões {}", respostaAvisoViagem);
		
		AvisoViagem novoAvisoViagem = avisoViagemDTO.toModel(cartao, userAgent, ip);
		cartao.adicionaAvisoViagem(novoAvisoViagem);
		executorTransacao.atualizaEComita(cartao);
		logger.info("Solicitação de Aviso de Viagem Criado {}", novoAvisoViagem);
	}

	
}
