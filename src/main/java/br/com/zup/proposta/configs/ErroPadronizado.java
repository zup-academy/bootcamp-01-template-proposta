package br.com.zup.proposta.configs;

import java.util.Collection;

public class ErroPadronizado {
    
    private Collection<String> mensagens;

    public ErroPadronizado(Collection<String> mensagens) {
        this.mensagens = mensagens;
    }

    public Collection<String> getMensagens() {
        return this.mensagens;
    }

}
