package br.com.zup.nossocartao.integracao.carteiradigital;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "carteiraDigital", url = "${api.cartoes}")
public interface CarteiraFeign {

	@RequestMapping(method = RequestMethod.POST, value = "/cartoes/{id}/carteiras")
	ResponseEntity<PaypalResponseFeign> associarCarteiraPaypal(@PathVariable(value = "id") String id,
			@RequestBody PaypalRequestFeign request);

}