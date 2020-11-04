package br.com.cartao.proposta.repository;

import br.com.cartao.proposta.domain.model.Biometria;
import org.springframework.data.repository.CrudRepository;

public interface BiometriaRepository extends CrudRepository<Biometria, String> {
}
