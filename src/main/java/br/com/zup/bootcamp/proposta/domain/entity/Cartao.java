package br.com.zup.bootcamp.proposta.domain.entity;

import br.com.zup.bootcamp.proposta.domain.service.enums.EstadoCartao;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private UUID idCartaoEmitido;

    @CreationTimestamp
    private LocalDateTime emitidoEm;

    @NotBlank
    private String titular;

    @OneToMany(mappedBy = "cartao",cascade = CascadeType.MERGE)
    private final List<Bloqueio> bloqueios = new ArrayList<>();

    @OneToMany(mappedBy = "cartao",cascade = CascadeType.MERGE)
    private final List<Aviso> avisos = new ArrayList<>();

    @OneToMany(mappedBy = "cartao",cascade = CascadeType.MERGE)
    private Set<Carteira> carteiras = new HashSet<>();

    @Positive
    private BigDecimal limite;

    @OneToMany(mappedBy = "cartao",cascade = CascadeType.MERGE)
    private final List<Biometria> biometrias = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private EstadoCartao estadoCartao;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotNull UUID idCartaoEmitido, LocalDateTime emitidoEm, @NotBlank String titular, @NotBlank BigDecimal limite) {
        this.idCartaoEmitido = idCartaoEmitido;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.estadoCartao = EstadoCartao.DESBLOQUEADO;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public UUID getIdCartaoEmitido() {
        return idCartaoEmitido;
    }

    public String getId() {
        return id;
    }

    public EstadoCartao getEstadoCartao() {
        return estadoCartao;
    }

    public void setEstadoCartao(EstadoCartao estadoCartao) {
        this.estadoCartao = estadoCartao;
    }

    public Set<Carteira> getCarteiras() {
        return carteiras;
    }

    public void adicionarBiometriaNoCartao(@NotNull byte[] fingerprint) {
        this.biometrias.add(new Biometria(fingerprint, this));
    }

    public void adicionarBloqueioDoCartao(@NotBlank String ip, @NotBlank String sistemaResponsavel  ) {
        setEstadoCartao(EstadoCartao.BLOQUEADO);
        this.bloqueios.add(new Bloqueio(ip, sistemaResponsavel, this));
    }

    public void adicionarAvisoDeViagem(Aviso aviso, HttpServletRequest httpRequest) {
        Assert.notNull(aviso, "O aviso não pode ser nulo para associação com o cartão");
        this.avisos.add(new Aviso(aviso.getValidoAte(), aviso.getDestino(), httpRequest.getRemoteAddr(),
                httpRequest.getHeader("User-Agent"), this));
    }

    public void adicionarCarteira(Carteira carteira) {
        Assert.notNull(carteira, "A carteira não pode ser nula para associação com o cartão");
        this.carteiras.add(new Carteira(carteira.getEmail(), carteira.getCarteira(), this));
    }
}
