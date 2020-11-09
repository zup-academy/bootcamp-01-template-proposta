package br.com.zup.proposta.proposta;

import br.com.zup.proposta.analiseproposta.StatusAvaliacaoProposta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, String> {
    public Optional<String> findByDocumento(String documento);
    Collection<Proposta> findByStatusAvaliacao(StatusAvaliacaoProposta statusAvaliacao);
}
