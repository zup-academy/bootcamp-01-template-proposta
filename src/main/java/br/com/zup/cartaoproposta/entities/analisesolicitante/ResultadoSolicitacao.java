package br.com.zup.cartaoproposta.entities.analisesolicitante;

import br.com.zup.cartaoproposta.entities.proposta.StatusProposta;

/**
 * Contagem de carga intrínseca da classe: 2
 */

public enum ResultadoSolicitacao {
    SEM_RESTRICAO, COM_RESTRICAO;

    //1
    public StatusProposta normalizacao(){
        //1
        if (this.equals(COM_RESTRICAO)) {
            return StatusProposta.NAO_ELEGIVEL;
        }
        return StatusProposta.AGUARDANDO_CARTAO;
    }
}
