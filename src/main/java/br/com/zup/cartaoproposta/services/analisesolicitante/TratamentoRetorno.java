package br.com.zup.cartaoproposta.services.analisesolicitante;

import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitanteRetorno;

public interface TratamentoRetorno {
    AnaliseSolicitanteRetorno analiseSolicitante(String documentoSolicitante, String nomeSolicitante, String idProposta);
}
