package br.com.b2w.starWars.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b2w.starWars.client.SwapiClient;
import br.com.b2w.starWars.client.dto.ResultResponse;
import br.com.b2w.starWars.dto.PlanetConverter;
import br.com.b2w.starWars.dto.PlanetDTO;
import br.com.b2w.starWars.exception.PlanetBadRequestException;
import br.com.b2w.starWars.exception.PlanetNotFoundException;
import br.com.b2w.starWars.exception.PlanetServerErrorException;
import br.com.b2w.starWars.model.Planet;
import br.com.b2w.starWars.repository.PlanetRepository;

@Service
public class PlanetService {

	@Autowired
	PlanetRepository planetRepository;

	@Autowired
	SwapiClient swapiClient;

	@Autowired
	PlanetConverter planetConverter;

	public Planet savePlanet(PlanetDTO dto) {

		isPlanetExistByName(dto.getName());

		try {

			Planet planet = planetConverter.toEntity(dto);
			planet.setMovies(checkMovies(dto.getName()));
			return Optional.of(planetRepository.save(planet)).orElseThrow(
					() -> new PlanetServerErrorException(PlanetServerErrorException.MESSAGES.CREATE_ERROR));

		} catch (Exception e) {
			throw new PlanetServerErrorException(e.getMessage());
		}
	}

	public List<Planet> getAllPlanets() {
		try {
			return planetRepository.findAll();
		} catch (Exception e) {
			throw new PlanetServerErrorException(PlanetServerErrorException.MESSAGES.FIND_ERROR);
		}
	}

	public Planet findPlanetByID(String id) {
		try {
			return planetRepository.findById(id)
					.orElseThrow(() -> new PlanetNotFoundException(PlanetNotFoundException.MESSAGES.PLANET_NOT_FOUND));
		} catch (PlanetNotFoundException e) {
			throw new PlanetNotFoundException(PlanetNotFoundException.MESSAGES.PLANET_NOT_FOUND);
		}catch (Exception e) {
			throw new PlanetServerErrorException(PlanetServerErrorException.MESSAGES.FIND_ERROR);
		} 
		
	}

	public void deletePlanById(String id) {

		isPlanetExistById(id);
		try {
			planetRepository.deleteById(id);
		} catch (Exception e) {
			throw new PlanetServerErrorException(PlanetServerErrorException.MESSAGES.DELETE_ERROR);
		}

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
			throw new PlanetServerErrorException(PlanetServerErrorException.MESSAGES.CLIENT_ERROR);
		}

	}

	public void isPlanetExistByName(String param) {

		if (planetRepository.findByName(param).isPresent()) {
			throw new PlanetBadRequestException(PlanetBadRequestException.MESSAGES.PLANET_EXIST);
		}

	}

	public void isPlanetExistById(String param) {

		planetRepository.findById(param)
				.orElseThrow(() -> new PlanetNotFoundException(PlanetNotFoundException.MESSAGES.PLANET_NOT_FOUND));

	}

}
