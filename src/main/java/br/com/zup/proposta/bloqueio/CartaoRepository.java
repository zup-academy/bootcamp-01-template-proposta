package br.com.zup.proposta.bloqueio;

import br.com.zup.proposta.cartao.Cartao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, String> {
}
