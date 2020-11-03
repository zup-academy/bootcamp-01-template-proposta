package br.com.zup.cartaoproposta.repositories;

import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.entities.proposta.StatusProposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Contagem de carga intrínseca da classe: 2
 */

//1
public interface PropostaRepository extends JpaRepository<Proposta, String> {
    Optional<Proposta> findByDocumento(String documento);
    //1
    List<Proposta> findByStatusProposta(StatusProposta statusProposta);
}
