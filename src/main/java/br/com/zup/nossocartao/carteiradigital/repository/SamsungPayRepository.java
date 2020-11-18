package br.com.zup.nossocartao.carteiradigital.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.core.support.DefaultCrudMethods;

import br.com.zup.nossocartao.carteiradigital.SamsungPay;

public interface SamsungPayRepository extends CrudRepository<SamsungPay, Long> {

}
