package br.com.proposta.repositories;

import br.com.proposta.models.Cartao;
import br.com.proposta.models.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    Cartao findByIdProposta(String propostaId);

}
