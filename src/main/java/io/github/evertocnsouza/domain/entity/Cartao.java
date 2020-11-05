package io.github.evertocnsouza.domain.entity;

import io.github.evertocnsouza.domain.enums.StatusCartao;
import io.github.evertocnsouza.domain.enums.TipoCarteira;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Cartao {
    //8 PCI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private UUID numeroCartao;

    @NotNull
    private LocalDateTime emitidoEm;

    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;
    //PCI 1

    @OneToMany
    private Set<Biometria> biometrias = new HashSet<>();
    //PCI 2

    @OneToMany
    private Set<Bloqueio> bloqueios = new HashSet<>();
    //PCI 3

    @OneToMany
    private Set<AvisoViagem> avisos = new HashSet<>();
    //PCI 4

    @OneToMany
    private Set<Carteira> carteiras = new HashSet<>();
    //PCI 5

    @Deprecated
    public Cartao(){
    }

    public Cartao(UUID numeroCartao, LocalDateTime emitidoEm) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.statusCartao = statusCartao.DESBLOQUEADO;
    }

    public void addBiometria(Biometria biometria) {
        biometrias.add(biometria);
    }

    public void addAvisoViagem(AvisoViagem avisoViagem) {
        avisos.add(avisoViagem);
    }

    public void addBloqueioDoCartao(Bloqueio bloqueio){
        bloqueios.add(bloqueio);
    }

    public void addCarteira(Carteira carteira){
        carteiras.add(carteira);
    }

    public void bloquearCartao() {
        this.statusCartao= statusCartao.BLOQUEADO;
    }

    public boolean verificarCartaoBloqueado() {
        return statusCartao.equals(statusCartao.BLOQUEADO);
    }
    //PCI 7;

    public Long getId() {
        return id;
    }

    public UUID getNumeroCartao() {
        return numeroCartao;
    }

    public boolean carteiraAssociadaCartao(TipoCarteira tipoCarteira) {
        return carteiras.stream().anyMatch(carteira -> carteira.CarteiraJaAdicionada(tipoCarteira));
    //PCI 8;
    }
}