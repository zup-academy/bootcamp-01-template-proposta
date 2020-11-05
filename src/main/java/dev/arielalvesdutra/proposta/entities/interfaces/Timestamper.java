package dev.arielalvesdutra.proposta.entities.interfaces;

import java.time.temporal.Temporal;

/**
 * Interface para entidades que devem ter o registro de criação
 * e última atualização.
 */
public interface Timestamper {

    Temporal getCadastradoEm();

    Temporal getAtualizadoEm();
}
