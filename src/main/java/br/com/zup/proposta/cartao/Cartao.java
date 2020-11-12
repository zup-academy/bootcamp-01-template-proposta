package br.com.zup.proposta.cartao;

import br.com.zup.proposta.aviso.Aviso;
import br.com.zup.proposta.biometria.Biometria;
import br.com.zup.proposta.bloqueio.Bloqueio;
import br.com.zup.proposta.carteira.Carteira;
import br.com.zup.proposta.carteira.TipoCarteira;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cartao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private @NotBlank String numeroCartao;
    private @NotNull LocalDateTime emitidoEm;
    private @NotBlank String titular;
    private @OneToMany Set<Biometria> biometrias = new HashSet<>();
    private @OneToMany Set<Bloqueio> bloqueios = new HashSet<>();
    private @OneToMany Set<Aviso> avisos = new HashSet<>();
    private @OneToMany Set<Carteira> carteiras = new HashSet<>();
    private @Enumerated(EnumType.STRING) StatusCartao status;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotBlank String numeroCartao, @NotNull LocalDateTime emitidoEm, @NotBlank String titular) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.status = StatusCartao.ATIVO;
    }

    public void addBiometria(Biometria biometria) {
        biometrias.add(biometria);
    }

    public void addBloqueio(Bloqueio bloqueio) {
        bloqueios.add(bloqueio);
    }

    public void addAviso(Aviso aviso) {
        avisos.add(aviso);
    }

    public void addCarteira(Carteira carteira) {
        carteiras.add(carteira);
    }

    public boolean verificarSeCartaoJaPossuiCarteiraAssociada(TipoCarteira tipoCarteira) {
        return carteiras.stream().anyMatch(carteira -> carteira.verificarSeExisteTipoCarteira(tipoCarteira));
    }

    public String getId() {
        return id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void bloquearCartao() {
        this.status = StatusCartao.BLOQUEADO;
    }
}
