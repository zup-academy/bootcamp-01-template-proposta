package br.com.zup.proposta.model;

import br.com.zup.proposta.model.enums.StatusCartao;
import br.com.zup.proposta.model.enums.TipoCarteira;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private @NotBlank String numero;

    private @NotNull @OneToOne Proposta proposta; //1

    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;
    @ElementCollection
    private Set<Biometria> biometrias = new HashSet<>(); //2
    @OneToMany(mappedBy = "cartao")
    private List<Bloqueio> bloqueios = new ArrayList<>(); //3
    @OneToMany(mappedBy = "cartao")
    //preciso colocar? não foi solicitado endpoint do cartão
    private List<AvisoViagem> avisoViagems = new ArrayList<>(); //4
    @OneToMany(mappedBy = "cartao")
    private List<Carteira> carteiras = new ArrayList<>(); //5

    @Deprecated
    public Cartao() {}

    public Cartao(@NotBlank String numero, @NotNull @Valid Proposta proposta) {
        this.numero = numero;
        this.proposta = proposta;
        this.statusCartao = StatusCartao.ATIVO;
    }

    public UUID getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void bloquearCartao(){
        this.statusCartao = StatusCartao.BLOQUEADO;
    }

    public String emailDonocartao(){
        return proposta.getEmail();
    }

    public void associaBiometria(String digital){
        this.biometrias.add(new Biometria(digital));
    }

    public boolean verificaAssociacaoCarteira(TipoCarteira tipoCarteira){
        return this.carteiras.stream()
                .anyMatch(c -> c.tipoDeCarteiraJaAssociada(tipoCarteira)); //6
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "numero='" + numero + '\'' +
                ", statusCartao=" + statusCartao +
                ", biometrias=" + biometrias +
                ", bloqueios=" + bloqueios +
                ", avisoViagems=" + avisoViagems +
                '}';
    }

}
