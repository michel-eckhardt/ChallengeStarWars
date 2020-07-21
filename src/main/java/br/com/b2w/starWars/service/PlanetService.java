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

@Service
public class PlanetService {

	@Autowired
	PlanetRepository planetRepository;

	@Autowired
	SwapiClient swapiGateway;

	@Autowired
	PlanetConverter planetConverter;

	public Optional<Planet> savePlanet(PlanetDTO dto) {

		if (isPlanetExist(dto.getName())) {
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

		if (isPlanetExist(id)) {
			planetRepository.deleteById(id);
			return true;
		}
		return false;

	}

	public Integer checkMovies(String planet) {
		try {
			int page = 1;
			var response = swapiGateway.findPlanetByName(planet, page);
			
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
				response = swapiGateway.findPlanetByName(planet, page);
			}

			return 0;

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
