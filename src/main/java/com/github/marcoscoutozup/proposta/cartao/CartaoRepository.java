package com.github.marcoscoutozup.proposta.cartao;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CartaoRepository extends CrudRepository<Cartao, UUID> {
}
