package br.com.b2w.starWars.gateway;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.b2w.starWars.gateway.dto.Response;

@FeignClient(value = "swapi-gateway", url = "https://swapi.dev/api/")
public interface SwapiGateway {
	
	@GetMapping(path= "planets/", produces = APPLICATION_JSON_VALUE)
	public Response findPlanetByName(@RequestParam(value = "search") String planet);

}
