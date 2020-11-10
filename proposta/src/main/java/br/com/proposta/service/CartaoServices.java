package br.com.proposta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.proposta.component.ExecutorTransacao;
import br.com.proposta.dto.CartaoConsultaDTO;
import br.com.proposta.dto.CartaoRetornoDTO;
import br.com.proposta.model.Cartao;
import br.com.proposta.model.Proposta;

//Contagem de Pontos - TOTAL:4
//1 - CartaoConsultaDTO
//1 - CartaoRetornoDTO
//1 - Cartao
//1 - Proposta

@Service
public class CartaoServices {
	
	@Autowired
	private ExecutorTransacao executorTransacao;
	
	private Logger logger = LoggerFactory.getLogger(CartaoServices.class);

	public void criaCartao (Proposta proposta) {
		RestTemplate restTemplate = new RestTemplate();
		
		CartaoConsultaDTO request = new CartaoConsultaDTO(proposta);	
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8888/api/cartoes",request, String.class);
		String retorno = response.getHeaders().getLocation().toString();
		String [] resultado = retorno.split("/");
		
		ResponseEntity<CartaoRetornoDTO> responseGet = restTemplate.getForEntity("http://localhost:8888/api/cartoes/"+resultado[4], CartaoRetornoDTO.class);
		
		Cartao cartaoCriado = responseGet.getBody().toModel();
		executorTransacao.salvaEComita(cartaoCriado);
		logger.info("Cartao criado {}", cartaoCriado);
	}
	
}
