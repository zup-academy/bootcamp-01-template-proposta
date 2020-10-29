package br.com.proposta.repositories;

import br.com.proposta.models.Carteira;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends CrudRepository<Carteira, String> {
}
