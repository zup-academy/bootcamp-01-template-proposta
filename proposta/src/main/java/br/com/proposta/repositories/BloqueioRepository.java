package br.com.proposta.repositories;

import br.com.proposta.entidades.Bloqueio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueioRepository extends CrudRepository<Bloqueio, String> {
}
