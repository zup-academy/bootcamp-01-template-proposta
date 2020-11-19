package com.proposta.criacaoproposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaRequest {

    @NotBlank
    private String documento;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    public PropostaRequest(@NotBlank String documento, @Email @NotBlank String email,
                           @NotBlank String nome, @NotBlank String endereco,
                           @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel(EntityManager manager) {
        if ((documento.length()) != 11 && (documento.length()) != 14) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Documento inválido.");
        }
        else if (!validarDocumento(manager, documento)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta inadequada.");
        }

        return new Proposta(new DocumentoLimpo(documento), email, nome, endereco, salario);
    }

    public boolean validarDocumento(EntityManager manager, String documento) {

        return manager.createQuery(
                "select p.documento from Proposta p where p.documento = :documento")
                .setParameter("documento", documento)
                .getResultList().isEmpty();
    }

    public String getDocumento() {
        return documento;
    }

}
