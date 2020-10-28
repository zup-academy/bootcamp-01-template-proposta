package br.com.zup.proposta.service.validadores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.zup.proposta.configs.exceptions.PropostaDuplicadaException;
import br.com.zup.proposta.controllers.form.SolicitacaoForm;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.repositories.PropostaRepository;

@Component
public class PropostaDuplicadaValidador implements Validator {

    @Autowired
    private PropostaRepository propostaRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SolicitacaoForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        SolicitacaoForm form = (SolicitacaoForm)target;

        Optional<Proposta> proposta = propostaRepository.findByDocumento(form.getDocumento());
        
        if(proposta.isPresent()) {
            throw new PropostaDuplicadaException("Proposta já existe para o solicitante.");
        }
    }
    
}
