package br.com.proposta.entidades.utils;

import br.com.proposta.validacoes.interfaces.CpfCnpj;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import javax.validation.constraints.NotBlank;

public class IdentificacaoDescriptografada {

    @NotBlank
    @CpfCnpj
    private String identificacaoDescriptografada;

    public IdentificacaoDescriptografada(String identificacaoDescriptografada) {
        this.identificacaoDescriptografada = identificacaoDescriptografada;
    }

    public String criptografa(){

        Assert.hasLength(identificacaoDescriptografada, "identificacao nao pode ser em branco");
        return new BCryptPasswordEncoder().encode(this.identificacaoDescriptografada);

    }
}
