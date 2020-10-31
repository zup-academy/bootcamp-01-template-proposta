package br.com.proposta.repositories;

import br.com.proposta.entidades.Aviso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisoRepository extends CrudRepository<Aviso, String> {
}
