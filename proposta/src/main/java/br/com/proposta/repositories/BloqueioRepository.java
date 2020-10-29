package br.com.proposta.repositories;

import br.com.proposta.models.Viagem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueioRepository extends CrudRepository<Viagem, Long> {
}
