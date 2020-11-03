package br.com.zup.cartaoproposta.repositories;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
}
