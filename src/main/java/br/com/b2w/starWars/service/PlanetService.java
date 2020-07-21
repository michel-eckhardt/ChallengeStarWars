package br.com.b2w.starWars.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b2w.starWars.dto.PlanetConverter;
import br.com.b2w.starWars.dto.PlanetDTO;
import br.com.b2w.starWars.gateway.SwapiGateway;
import br.com.b2w.starWars.model.Planet;
import br.com.b2w.starWars.repository.PlanetRepository;
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

		if (isPlanetExist(dto.getName())) {
			return null;
		}

		var totalMovies = this.checkForMovieAppearances(dto.getName());

		Planet planet = planetConverter.toEntity(dto);
		planet.setMovies(totalMovies);

		return Optional.of(planetRepository.save(planet));

	}

	public List<Planet> getAllPlanets() {
		return planetRepository.findAll();
	}

	public Optional<Planet> findPlanetByID(String id) {

		return planetRepository.findById(id);

	}

	public Boolean deletePlanById(String id) {

		if (isPlanetExist(id)) {
			planetRepository.deleteById(id);
			return true;
		}
		return false;

	}

	public Integer checkForMovieAppearances(String planet) {
		try {
			var response = swapiGateway.findPlanetByName(planet);

			return PlanetHelper.isListEmpty(response);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	public Boolean isPlanetExist(String param) {

		if (planetRepository.findByName(param).isPresent()) {
			return true;
		} else if (planetRepository.findById(param).isPresent()) {
			return true;
		}
		return false;

	}

}
