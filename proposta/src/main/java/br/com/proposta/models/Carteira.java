package br.com.proposta.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String carteira;

    @OneToMany(mappedBy = "carteira")
    private Set<Cartao> cartoes = new HashSet<>();

    public Carteira(String carteira, Set<Cartao> cartoes) {
        this.carteira = carteira;
        this.cartoes = cartoes;
    }

}
