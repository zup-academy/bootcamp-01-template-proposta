package dev.arielalvesdutra.proposta.repositories;

import dev.arielalvesdutra.proposta.entities.SolicitacaoRecuperacaoSenha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitacaoRecuperacaoSenhaRepository extends JpaRepository<SolicitacaoRecuperacaoSenha, String> {
}
