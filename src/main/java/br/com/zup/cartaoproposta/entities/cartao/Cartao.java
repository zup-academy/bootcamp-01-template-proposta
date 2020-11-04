package br.com.zup.cartaoproposta.entities.cartao;

import br.com.zup.cartaoproposta.entities.cartao.aviso.AvisoCartao;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioCartao;
import br.com.zup.cartaoproposta.entities.cartao.carteira.CarteiraCartao;
import br.com.zup.cartaoproposta.entities.cartao.parcela.ParcelaCartao;
import br.com.zup.cartaoproposta.entities.cartao.renegociacao.RenegociacaoCartao;
import br.com.zup.cartaoproposta.entities.cartao.vencimento.VencimentoCartao;
import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Contagem de carga intrínseca da classe: 7
 */

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private String idLegado;

    @NotNull
    private LocalDateTime emitidoEm;
    @NotNull
    private String titular;
    @ElementCollection
    //1
    private List<BloqueioCartao> bloqueios = new ArrayList<>();
    @ElementCollection
    //1
    private List<AvisoCartao> avisos = new ArrayList<>();
    @ElementCollection
    //1
    private List<CarteiraCartao> carteiras = new ArrayList<>();
    @ElementCollection
    //1
    private List<ParcelaCartao> parcelas = new ArrayList<>();
    @NotNull
    private int limite;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "id", column = @Column(name = "id_renegociacao")),
            @AttributeOverride( name = "quantidade", column = @Column(name = "quantidade_renegociacao")),
            @AttributeOverride( name = "valor", column = @Column(name = "valor_renegociacao")),
            @AttributeOverride( name = "dataDeCriacao", column = @Column(name = "data_de_criacao_renegociacao"))
    })
    //1
    private RenegociacaoCartao renegociacao;
    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "id", column = @Column(name = "id_vencimento")),
            @AttributeOverride( name = "dia", column = @Column(name = "dia_vencimento")),
            @AttributeOverride( name = "dataDeCriacao", column = @Column(name = "data_de_criacao_vencimento"))
    })
    //1
    private VencimentoCartao vencimento;

    @OneToOne
    //1
    private Proposta proposta;

    @Deprecated
    public Cartao(){}

    public Cartao(String idLegado, LocalDateTime emitidoEm, String titular, int limite, VencimentoCartao vencimento, Proposta proposta) {
        this.idLegado = idLegado;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
        this.proposta = proposta;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public int getLimite() {
        return limite;
    }

    public Proposta getProposta() {
        return proposta;
    }
}
