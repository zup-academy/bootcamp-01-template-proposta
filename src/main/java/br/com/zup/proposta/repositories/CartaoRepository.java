package br.com.zup.proposta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.proposta.model.cartao.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
    
}
