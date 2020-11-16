package br.com.zup.cartaoproposta.entities.cartao.carteira;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Contagem de carga intrínseca da classe: 1
 */

@Entity
public class CarteiraCartao {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    @Email
    private String email;

    private LocalDateTime associadaEm = LocalDateTime.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    private Emissor emissor;

    @NotNull
    @Valid
    @ManyToOne
    //1
    private Cartao cartao;

    @Deprecated
    public CarteiraCartao(){}

    public CarteiraCartao(@NotBlank @Email String email, @NotNull Emissor emissor, @NotNull @Valid Cartao cartao) {
        this.email = email;
        this.emissor = emissor;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociadaEm() {
        return associadaEm;
    }

    public Emissor getEmissor() {
        return emissor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarteiraCartao)) return false;
        CarteiraCartao that = (CarteiraCartao) o;
        return emissor.equals(that.emissor) &&
                cartao.equals(that.cartao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emissor, cartao);
    }

    @Override
    public String toString() {
        return "CarteiraCartao{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", associadaEm=" + associadaEm +
                ", emissor='" + emissor + '\'' +
                ", cartao=" + cartao +
                '}';
    }
}
