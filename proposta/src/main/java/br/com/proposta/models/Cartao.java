package br.com.proposta.models;

import br.com.proposta.models.Enums.StatusBloqueio;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private OffsetDateTime emitidoEm;

    private String titular;

    @ElementCollection
    private Set<Biometria> biometria = new HashSet();

    @OneToMany(mappedBy = "cartao")
    private Set<Bloqueio> bloqueios = new HashSet<>();

    @OneToOne
    private Proposta proposta;

    private String idProposta;

    @Deprecated
    public Cartao(){}

    public Cartao(String titular, String idProposta) {
        this.emitidoEm = OffsetDateTime.now();
        this.titular = titular;
        this.idProposta = idProposta;
    }

    public void associarComProposta(EntityManager entityManager, Long propostaId){

        this.proposta = entityManager.find(Proposta.class, propostaId);

    }

    public void bloqueiaCartao(String internetProtocol, String userAgent, StatusBloqueio resposta){

        this.bloqueios.add(new Bloqueio(internetProtocol, userAgent, resposta, this));

    }

    public String getId() {
        return id;
    }

    public void adicionaBiometria(Biometria biometria){
        this.biometria.add(biometria);
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
