package br.com.zup.proposta.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.zup.proposta.model.Proposta;

public interface PropostaRepository extends CrudRepository<Proposta, String> {
    
}
