package br.com.zup.proposta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.controllers.dto.PropostaDto;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.repositories.PropostaRepository;

@Service
public class PropostaService {
    
    @Autowired
    private PropostaRepository repository;

    public PropostaDto criar(Proposta proposta) {
        final Proposta propostaCriada = repository.save(proposta);
        return propostaCriada.toDto();
    }
}
