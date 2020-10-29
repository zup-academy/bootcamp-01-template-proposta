package br.com.proposta.repositories;

import br.com.proposta.models.Biometria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiometriaRepository extends CrudRepository<Biometria, String> {
}
