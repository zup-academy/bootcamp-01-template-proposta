package dev.arielalvesdutra.proposta.test.unit.entities;


import dev.arielalvesdutra.proposta.entities.Cartao;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class CartaoTest {

    @Test
    public void equalsEHashCode_deveSerPorId() {
        var id = UUID.randomUUID().toString();

        var cartao1 = new Cartao().setId(id);
        var cartao2 = new Cartao().setId(id);

        assertThat(cartao1).isEqualTo(cartao2);

        Set<Cartao> cartaos = new HashSet<>(asList(cartao1));

        assertThat(cartaos).contains(cartao2);
    }
}
