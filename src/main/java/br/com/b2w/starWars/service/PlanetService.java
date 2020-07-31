package br.com.b2w.starWars.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b2w.starWars.client.SwapiClient;
import br.com.b2w.starWars.client.dto.ResultResponse;
import br.com.b2w.starWars.dto.PlanetConverter;
import br.com.b2w.starWars.dto.PlanetDTO;
import br.com.b2w.starWars.model.Planet;
import br.com.b2w.starWars.repository.PlanetRepository;
import br.com.b2w.starWars.util.Validation;

@Service
public class PlanetService {

	@Autowired
	PlanetRepository planetRepository;

	@Autowired
	SwapiClient swapiClient;
	
	@Autowired
	Validation validation;

	@Autowired
	PlanetConverter planetConverter;

	public Optional<Planet> savePlanet(PlanetDTO dto) {

	
		if (validation.isPlanetExist(dto.getName())) {
			return null;
		}

		Planet planet = planetConverter.toEntity(dto);
		planet.setMovies(checkMovies(dto.getName()));

		return Optional.of(planetRepository.save(planet));

	}

	public List<Planet> getAllPlanets() {
		return planetRepository.findAll();
	}

	public Optional<Planet> findPlanetByID(String id) {
		
		return planetRepository.findById(id);

	}

	public Boolean deletePlanById(String id) {

		if (validation.isPlanetExist(id)) {
			planetRepository.deleteById(id);
			return true;
		}
		return false;

	}

	public Integer checkMovies(String planet) {
		try {
			int page = 1;
			var response = swapiClient.findPlanetByName(planet, page);
			
			if (response.getResults().size() <= 10 && response.getNext() == null) {
				for (ResultResponse resultList : response.getResults()) {
					if (resultList.getName().equals(planet)) {
						return resultList.getFilms().size();
					}
				}
			}

			while (response.getNext() != null) {

				for (ResultResponse resultList : response.getResults()) {
					if (resultList.getName().equals(planet)) {
						return resultList.getFilms().size();
					}
				}
				page++;
				response = swapiClient.findPlanetByName(planet, page);
			}

			return 0;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}



}
