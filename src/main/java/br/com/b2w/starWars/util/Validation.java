package br.com.b2w.starWars.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.b2w.starWars.repository.PlanetRepository;

@Component
public class Validation {

	@Autowired
	PlanetRepository planetRepository;
	
	public Boolean isPlanetExist(String param) {

		if (planetRepository.findByName(param).isPresent()) {
			return true;
		} else if (planetRepository.findById(param).isPresent()) {
			return true;
		}
		return false;

	}
	
}
