package br.com.cartao.proposta.repository;

import br.com.cartao.proposta.domain.model.Proposta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, String> {
}
