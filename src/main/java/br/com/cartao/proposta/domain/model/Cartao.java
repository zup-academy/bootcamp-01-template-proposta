package br.com.cartao.proposta.domain.model;

import br.com.cartao.proposta.domain.enums.EstadoCartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Carga intrínseca máxima permitida - 9
 * Carga intrínseca da classe - 2
 */

@Entity
@Table(name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private String cartaoId;

    @Enumerated(EnumType.STRING)
    // +1
    private EstadoCartao estadoCartao;

    @OneToOne
    // +1
    private Proposta proposta;

    @Deprecated
    public Cartao() {
    }

    public Cartao(String cartaoId, Proposta proposta) {
        this.cartaoId = cartaoId;
        this.proposta = proposta;
        this.estadoCartao = EstadoCartao.ATIVO;
    }

    public String getId() {
        return id;
    }

    public String getCartaoId() {
        return cartaoId;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public EstadoCartao getEstadoCartao() {
        return estadoCartao;
    }

    public void MudaEstadoCartao(EstadoCartao estadoCartao){
        this.estadoCartao = estadoCartao;
    }

    public void estadoCartaoBloqueado(){
        this.estadoCartao = EstadoCartao.BLOQUEADO;
    }

    public void estadoCartaoComFalha(){
        this.estadoCartao = EstadoCartao.FALHA;
    }

}
