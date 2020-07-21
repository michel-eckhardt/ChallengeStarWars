package br.com.b2w.starWars.util;

import org.springframework.stereotype.Component;

import br.com.b2w.starWars.gateway.dto.Response;

@Component
public final class PlanetHelper {
	
	

	public static Integer isListEmpty(Response resp) {
		
		return resp.getResults().isEmpty() || resp.getResults() == null? 0 : resp.getResults().get(0).getFilms().size();
	}
	
	
	
}
