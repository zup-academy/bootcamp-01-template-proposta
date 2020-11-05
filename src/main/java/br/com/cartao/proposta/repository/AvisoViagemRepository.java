package br.com.cartao.proposta.repository;

import br.com.cartao.proposta.domain.model.AvisoViagem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvisoViagemRepository extends CrudRepository<AvisoViagem, String> {

    Optional<AvisoViagem> findByCartaoCartaoIdAndDestinoViagem(String cartaoId, String destinoViagem);
}
