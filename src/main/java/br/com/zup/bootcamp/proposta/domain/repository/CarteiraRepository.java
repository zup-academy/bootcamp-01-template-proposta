package br.com.zup.bootcamp.proposta.domain.repository;

import br.com.zup.bootcamp.proposta.domain.entity.Carteira;
import br.com.zup.bootcamp.proposta.domain.service.enums.EmpresaAssociada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, String> {
    Carteira findFirstByOrderBySolicitadoEmDesc();
    Optional<Carteira> findByCartaoIdAndCarteira(String cartao, EmpresaAssociada carteira);
}
