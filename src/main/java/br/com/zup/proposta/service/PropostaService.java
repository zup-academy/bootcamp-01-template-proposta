package br.com.zup.proposta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.controllers.apiResponses.AnaliseResponse;
import br.com.zup.proposta.controllers.dto.PropostaDto;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.EstadoProposta;
import br.com.zup.proposta.repositories.PropostaRepository;
import br.com.zup.proposta.service.feign.AnaliseClient;

@Service
public class PropostaService {
    
    @Autowired
    private PropostaRepository repository;
    @Autowired
    private AnaliseClient analiseClient;

    public PropostaDto criar(Proposta proposta) {
        Proposta propostaCriada = repository.save(proposta);

        AnaliseResponse analiseResponse =  analiseClient.analiseProposta(propostaCriada.toAnaliseForm());
        if (analiseResponse.isElegivel()) {
            propostaCriada.setEstadoProposta(EstadoProposta.ELEGIVEL);
            repository.save(propostaCriada);
        }

        return propostaCriada.toDto();
    }
}
