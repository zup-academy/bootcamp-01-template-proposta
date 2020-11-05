package br.com.cartao.proposta.domain.model;

import br.com.cartao.proposta.domain.enums.CarteiraDigitalTipo;
import br.com.cartao.proposta.domain.enums.EstadoAvisoLegado;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "carteiraDigital")
public class CarteiraDigital {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Email @NotBlank
    private String email;
    @NotNull
    @Column(unique = true) @Enumerated(EnumType.STRING)
    private CarteiraDigitalTipo carteira;
    @Enumerated(EnumType.STRING)
    private EstadoAvisoLegado estadoAvisoLegado;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public CarteiraDigital() {
    }

    public CarteiraDigital(@Email @NotBlank String email, @NotBlank CarteiraDigitalTipo carteira, Cartao cartao) {
        this.email = email;
        this.carteira = carteira;
        this.cartao = cartao;
        this.estadoAvisoLegado = EstadoAvisoLegado.NAO_AVISADO;
    }

    public String getId() {
        return id;
    }

    public EstadoAvisoLegado getEstadoAvisoLegado() {
        return estadoAvisoLegado;
    }

    public String getEmail() {
        return email;
    }

    public CarteiraDigitalTipo getCarteira() {
        return carteira;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public String numeroCartao(){
        return this.cartao.getCartaoId();
    }

    public void associacaoSucessoCarteira(){
        this.estadoAvisoLegado = EstadoAvisoLegado.AVISADO;
    }

    public void associacaoFalhaCarteira(){
        this.estadoAvisoLegado = EstadoAvisoLegado.FALHA;
    }

}
