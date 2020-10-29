package io.github.evertocnsouza.rest.dto;

import com.sun.istack.NotNull;
import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.validation.annotation.CpfCnpj;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaRequest {

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
        @CpfCnpj
        @NotBlank
        private String documento;

        public PropostaRequest(@Email @NotBlank String email,
                                   @NotBlank String nome, @NotBlank String endereco,
                                   @Positive BigDecimal salario, String documento) {
            super();
            this.email = email;
            this.nome = nome;
            this.endereco = endereco;
            this.salario = salario;
            this.documento = documento;
        }

        public Proposta toModel() {
            return new Proposta(email,nome,endereco,salario,documento);
        }

        public String getDocumento() {
            return documento;
        }

    }
