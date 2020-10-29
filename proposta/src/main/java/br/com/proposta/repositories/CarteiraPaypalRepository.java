package br.com.proposta.repositories;

import br.com.proposta.models.CarteiraPaypal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraPaypalRepository extends CrudRepository<CarteiraPaypal, String> {
}
