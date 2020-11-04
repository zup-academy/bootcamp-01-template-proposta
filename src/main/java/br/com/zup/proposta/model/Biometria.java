package br.com.zup.proposta.model;

import br.com.zup.proposta.util.ConverterArquivo;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;

@Embeddable
public class Biometria {

    @NotNull
    private byte[] digital;
    private LocalDateTime instanteCriacao;

    @Deprecated
    public Biometria(){}

    public Biometria(String digital) {
        this.digital = digital.getBytes(); //1
        this.instanteCriacao = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Biometria)) return false;
        Biometria biometria = (Biometria) o;
        return Arrays.equals(digital, biometria.digital);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(digital);
    }

}
