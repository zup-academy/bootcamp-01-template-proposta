package br.com.proposta.repositories;

import br.com.proposta.entidades.Enums.StatusAvaliacaoProposta;
import br.com.proposta.entidades.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, String> {

    Optional<Proposta> findByIdentificacao(String identificacao);

    Collection<Proposta> findByStatusAndCartaoNull(StatusAvaliacaoProposta status);

}
