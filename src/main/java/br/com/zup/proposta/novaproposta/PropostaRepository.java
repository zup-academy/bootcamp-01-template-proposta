package br.com.zup.proposta.novaproposta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, Long> {
    public Optional<String> findByDocumento(String documento);
}
