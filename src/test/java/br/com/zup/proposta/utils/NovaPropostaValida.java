package br.com.zup.proposta.utils;

import br.com.zup.proposta.controllers.form.SolicitacaoForm;

public class NovaPropostaValida {
    
    public static SolicitacaoForm novaSolicitacaoValidaElegivel() {
        return new SolicitacaoForm("44446831550", "email.test.4845@zup.com.br", 
            "Gustavo Barros", "974 Yago Travessa", 6000d);
    }

    public static SolicitacaoForm novaSolicitacaoValidaNaoElegivel() {
        return new SolicitacaoForm("30747519080", "email.test.7189@zup.com.br", 
            "Rafaela Carvalho", "99802 Alessandra Viela", 7754d);
    }
}
