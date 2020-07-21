package br.com.b2w.starWars.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b2w.starWars.dto.PlanetConverter;
import br.com.b2w.starWars.dto.PlanetDTO;
import br.com.b2w.starWars.exception.AttributeException;
import br.com.b2w.starWars.gateway.SwapiGateway;
import br.com.b2w.starWars.model.Planet;
import br.com.b2w.starWars.repository.PlanetRepository;
import br.com.b2w.starWars.resource.PlanetController;
import br.com.b2w.starWars.util.Constants;
import br.com.b2w.starWars.util.PlanetHelper;

@Service
public class PlanetService {

	@Autowired
	PlanetRepository planetRepository;

	@Autowired
	SwapiGateway swapiGateway;

	@Autowired
	PlanetConverter planetConverter;

	public Optional<Planet> savePlanet(PlanetDTO dto) {

		if(isPlanetExist(dto.getName())) {
			return null;
		}
		
		var totalMovies = this.checkForMovieAppearances(dto.getName());

		Planet planet = planetConverter.toEntity(dto);
		planet.setMovies(totalMovies);

		return Optional.of(planetRepository.save(planet));

	}

	public Integer checkForMovieAppearances(String planet) {
		try {
			var response = swapiGateway.findPlanetByName(planet);

			return PlanetHelper.isListEmpty(response.getResults());

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
	
	public Boolean isPlanetExist(String planetName) {

		Optional<Planet> planet = planetRepository.findByName(planetName);

		if (planet.isPresent()) {
			return true;
		}
		return false;

	}

}
