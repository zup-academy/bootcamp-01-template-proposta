package br.com.proposta.repositories;

import br.com.proposta.models.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    Optional<Proposta> findByIdentificacao(String identificacao);

}
