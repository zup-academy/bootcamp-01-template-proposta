package br.com.proposta.repositories;

import br.com.proposta.models.CarteiraSamsung;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraSamsungRepository extends CrudRepository<CarteiraSamsung, String> {
}
