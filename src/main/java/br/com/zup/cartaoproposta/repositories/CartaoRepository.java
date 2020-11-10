package br.com.zup.cartaoproposta.repositories;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Contagem de carga intrínseca da classe: 1
 */

//1
public interface CartaoRepository extends JpaRepository<Cartao, String> {
    Optional<Cartao> findByIdLegado(String idLegado);
}
