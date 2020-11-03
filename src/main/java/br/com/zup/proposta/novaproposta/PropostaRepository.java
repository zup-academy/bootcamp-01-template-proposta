package br.com.zup.proposta.novaproposta;

import br.com.zup.proposta.integracao.StatusAvaliacaoProposta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, String> {
    public Optional<String> findByDocumento(String documento);
    Collection<Proposta> findByStatusAvaliacao(StatusAvaliacaoProposta statusAvaliacao);
}
