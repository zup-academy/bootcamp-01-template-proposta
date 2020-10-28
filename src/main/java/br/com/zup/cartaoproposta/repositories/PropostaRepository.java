package br.com.zup.cartaoproposta.repositories;

import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, String> {
    Optional<Proposta> findByDocumento(String documento);
}
