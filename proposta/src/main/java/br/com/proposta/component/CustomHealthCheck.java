package br.com.proposta.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthCheck implements HealthIndicator{

	@Override
	public Health health() {	
		 Map<String, Object> details = new HashMap<>();
	     details.put("versão: ", "1.0.0");
	     details.put("descrição: ", "------- TESTE CUSTOM HEALTH CHECK -------");
	     details.put("endereçoL: ", "000.0.0.1");
	        
	     return Health.status(Status.UP).withDetails(details).build();
	}

}
