package br.com.b2w.starWars.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.b2w.starWars.model.Planet;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> {

	Optional<Planet> findById(String id);

	Optional<Planet> findByName(String name);

}