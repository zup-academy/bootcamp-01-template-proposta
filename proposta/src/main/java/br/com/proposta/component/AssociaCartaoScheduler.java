package br.com.proposta.component;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import br.com.proposta.dto.CartaoRetornoDTO;
import br.com.proposta.dto.enums.StatusAvaliacaoProposta;
import br.com.proposta.model.Cartao;
import br.com.proposta.model.Proposta;
import br.com.proposta.repository.CartaoRepository;
import br.com.proposta.repository.PropostaRepository;

//Contagem de Pontos - TOTAL:7
//1 - ExecutorTransacao
//1 - PropostaRepository
//1 - CartaoRepository
//1 - CartaoRetornoDTO
//1 - Cartao
//1 - Proposta
//1 - For

@Component
public class AssociaCartaoScheduler {
	
	@Autowired
	private ExecutorTransacao executorTransacao;
	
	@Autowired
	private PropostaRepository propostaRepository;
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@PersistenceContext
	private EntityManager manager;
	
	private static final Logger log = LoggerFactory.getLogger(AssociaCartaoScheduler.class);

	
	@Scheduled(fixedDelayString = "${periodicidade.executa-associacao-cartao}")
	public void associaCartao() {
		List<Proposta> propostas = propostaRepository.todasSemCartao(StatusAvaliacaoProposta.elegivel);
		List<Cartao> listacartoes = cartaoRepository.cartaoSemProposta();
		RestTemplate restTemplate = new RestTemplate();
		
		log.info("Existem {} propostas para avaliar",propostas.toString());
		log.info("Existem {} cartoes sem proposta",listacartoes.toString());
		
		for (Cartao cartao : listacartoes) {
			ResponseEntity<CartaoRetornoDTO> responseGet = restTemplate.getForEntity("http://localhost:8888/api/cartoes/"+cartao.getNumero(), CartaoRetornoDTO.class);
			
			log.info("Retorno consulta dados cartao {}",responseGet.getBody());
			
			Long idProposta = Long.parseLong(responseGet.getBody().getIdProposta());
			Proposta propostaEncontrada = manager.find(Proposta.class, idProposta);
			
			Assert.notNull(propostaEncontrada,"Proposta não encontrada para o ID: " + idProposta);
			
			propostaEncontrada.associaCartao(cartao);
			cartao.setProposta(propostaEncontrada);
			executorTransacao.atualizaEComita(propostaEncontrada);
			
			log.info("Proposta foi atualizada com sucesso {}",propostaEncontrada.toString());
		}
	}	
}
