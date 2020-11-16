package dev.arielalvesdutra.proposta.repositories;

import dev.arielalvesdutra.proposta.entities.Carteira;
import dev.arielalvesdutra.proposta.entities.enums.CarteiraTipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, String> {

    /**
     * Retorna verdadeiro caso já exista uma carteira cadastrada com
     * o ID cartão e tipo de cartão passados por parâmetro.
     *
     * @param cartaoId ID do cartão da carteira.
     * @param carteiraTipo Tipo da carteira.
     * @return
     */
    boolean existsByCartaoIdAndTipo(String cartaoId, CarteiraTipo carteiraTipo);
}
