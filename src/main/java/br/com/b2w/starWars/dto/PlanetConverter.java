package br.com.b2w.starWars.dto;

import org.springframework.stereotype.Component;

import br.com.b2w.starWars.model.Planet;

@Component
public class PlanetConverter {

	public Planet toEntity(PlanetDTO dto) {
		
		Planet planet = new Planet();
		planet.setName(dto.getName());
		planet.setTerrain(dto.getTerrain());
		planet.setClimate(dto.getClimate());
		return planet;
		
	}
		
}
