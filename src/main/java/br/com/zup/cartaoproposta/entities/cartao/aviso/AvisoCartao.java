package br.com.zup.cartaoproposta.entities.cartao.aviso;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 1
 */

@Entity
public class AvisoCartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private LocalDate validoAte;
    @NotBlank
    private String destino;
    private LocalDateTime avisoEm = LocalDateTime.now();

    @NotBlank
    private String ip;
    @NotBlank
    private String userAgent;

    @Valid
    @ManyToOne
    //1
    private Cartao cartao;

    @Deprecated
    public AvisoCartao() {}

    public AvisoCartao(@NotNull LocalDate validoAte, @NotBlank String destino, @NotBlank String ip, @NotBlank String userAgent, @Valid Cartao cartao) {
        this.validoAte = validoAte;
        this.destino = destino;
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AvisoCartao{" +
                "id='" + id + '\'' +
                ", validoAte=" + validoAte +
                ", destino='" + destino + '\'' +
                ", avisoEm=" + avisoEm +
                ", ip='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", cartao=" + cartao +
                '}';
    }
}
