package br.com.cartao.proposta.repository;

import br.com.cartao.proposta.domain.model.Proposta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, String> {

    Optional<Proposta> findByDocumento(String documento);
}
