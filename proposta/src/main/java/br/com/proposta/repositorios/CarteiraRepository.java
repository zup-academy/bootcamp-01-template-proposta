package br.com.proposta.repositorios;

import br.com.proposta.entidades.Carteira;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends CrudRepository<Carteira, String> {
}
