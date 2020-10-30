package br.com.zup.proposta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.controllers.apiResponses.AnaliseResponse;
import br.com.zup.proposta.controllers.dto.PropostaDto;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.EstadoProposta;
import br.com.zup.proposta.repositories.PropostaRepository;
import br.com.zup.proposta.service.feign.AnaliseClient;
import feign.FeignException.UnprocessableEntity;

@Service
public class PropostaService {

    private static final Logger logger = LoggerFactory.getLogger(PropostaService.class);

    @Autowired
    private PropostaRepository repository;
    @Autowired
    private AnaliseClient analiseClient;

    public PropostaDto criar(Proposta proposta) {
        Proposta propostaCriada = repository.save(proposta);
        logger.info("Proposta criada", propostaCriada.toString());

        try {
            AnaliseResponse analiseResponse = analiseClient.analiseProposta(propostaCriada.toAnaliseForm());
            logger.info("Analise recebida do endpoint", analiseResponse.toString());

            if (analiseResponse.isElegivel()) {
                propostaCriada.setEstadoProposta(EstadoProposta.ELEGIVEL);
                repository.save(propostaCriada);
                logger.info("Proposta marcada como Elegível", propostaCriada.toString());
            }

        } catch (UnprocessableEntity e) {
            logger.info("Proposta Não Elegível", propostaCriada.toString());
            return propostaCriada.toDto();
        }
        
        logger.info("Retornando DTO");
        return propostaCriada.toDto();
    }

    public Proposta buscarPorId(String id) {
        return repository.findById(id).orElseThrow(() -> 
            new IllegalStateException("Proposta com id " + id + " não encontrada"));
    }

    public void removeTudo() {
        repository.deleteAll();
    }
}
