package br.com.zup.cartaoproposta.entities.cartao.biometria;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 1
 */

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotEmpty
    @Column(columnDefinition="text")
    private String biometria;

    @ManyToOne
    @NotNull
    @Valid
    //1
    private Cartao cartao;

    private LocalDateTime dataCadastro = LocalDateTime.now();

    @Deprecated
    public Biometria(){}

    public Biometria(@NotEmpty String biometria, Cartao cartao) {
        this.biometria = biometria;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getBiometria() {
        return biometria;
    }

    @Override
    public String toString() {
        return "Biometria{" +
                "id='" + id + '\'' +
                ", biometria='" + biometria + '\'' +
                '}';
    }
}
