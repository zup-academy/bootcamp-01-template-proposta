package br.com.zup.proposta.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.zup.proposta.model.Proposta;

public interface PropostaRepository extends JpaRepository<Proposta, String> {
    Optional<Proposta> findByDocumento(String documento);

    @Query("SELECT p FROM Proposta p WHERE p.cartaoCriado = false AND p.estadoProposta = 'ELEGIVEL'")
    Collection<Proposta> findByCartaoNaoCriado();
}
