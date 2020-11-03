package br.com.zup.cartaoproposta.entities.cartao;

import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    private String idClient;

    private LocalDateTime emitidoEm;
    private String titular;
//    @ElementCollection
//    //1
//    private List<BloqueioCartao> bloqueios;
//    @ElementCollection
//    //1
//    private List<AvisoCartao> avisos;
//    @ElementCollection
//    //1
//    private List<CarteiraCartao> carteiras;
//    @ElementCollection
//    //1
//    private List<ParcelaCartao> parcelas;
    private int limite;
//    @ElementCollection
//    //1
//    private RenegociacaoCartao renegociacao;
//    //1
//    private VencimentoCartao vencimento;

    @OneToOne
    //1
    private Proposta proposta;

    @Deprecated
    public Cartao(){}

    public Cartao(String idClient, LocalDateTime emitidoEm, String titular, int limite, Proposta proposta) {
        this.idClient = idClient;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.proposta = proposta;
    }

    //    public Cartao(String idClient, LocalDateTime emitidoEm, String titular, List<BloqueioCartao> bloqueios, List<AvisoCartao> avisos, List<CarteiraCartao> carteiras, List<ParcelaCartao> parcelas, int limite, RenegociacaoCartao renegociacao, VencimentoCartao vencimento, Proposta proposta) {
//        this.idClient = idClient;
//        this.emitidoEm = emitidoEm;
//        this.titular = titular;
//        this.bloqueios = bloqueios;
//        this.avisos = avisos;
//        this.carteiras = carteiras;
//        this.parcelas = parcelas;
//        this.limite = limite;
//        this.renegociacao = renegociacao;
//        this.vencimento = vencimento;
//        this.proposta = proposta;
//    }

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
