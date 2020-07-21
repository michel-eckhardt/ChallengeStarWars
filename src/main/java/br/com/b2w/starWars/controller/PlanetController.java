package br.com.b2w.starWars.controller;

import java.util.HashMap;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.b2w.starWars.dto.PlanetConverter;
import br.com.b2w.starWars.dto.PlanetDTO;
import br.com.b2w.starWars.exception.AttributeException;
import br.com.b2w.starWars.model.Planet;
import br.com.b2w.starWars.service.PlanetService;
import br.com.b2w.starWars.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/v1/planet")
@Api(value = "/v1/planet", tags = "Star Wars operations")
public class PlanetController {

	@Autowired
	PlanetService planetService;

	@Autowired
	PlanetConverter planetConverter;

	@PostMapping
	@ApiOperation(value = "Create Planet.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created Successfully."),
			@ApiResponse(code = 400, message = "The planet exist or some attribute sent is null.") })
	public ResponseEntity<?> createPlanet(@RequestBody @Valid PlanetDTO dto) throws AttributeException {

		Optional<Planet> planet = planetService.savePlanet(dto);
		try {

			if (planet == null) {

				HashMap<String, String> body = new HashMap<>();
				body.put(Constants.MESSAGE, Constants.PLANET_EXIST);

				var responseIfPlanetNull = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);

				return responseIfPlanetNull;
			}

			return ResponseEntity.status(HttpStatus.CREATED).body(planet);

		} catch (Exception e) {

			throw new RuntimeException(e.getMessage());
		}

	}

	@GetMapping
	@ApiOperation(value = "Find all planets")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Search Success."),
			@ApiResponse(code = 404, message = "The list of planet is empty.") })
	public ResponseEntity<?> findAllPlanets() {
		try {
			return ResponseEntity.ok().body(planetService.getAllPlanets());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get planet by Id.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Search Success."),
			@ApiResponse(code = 404, message = "The list of planet was not found.") })
	public ResponseEntity<?> findPlanetById(@PathVariable(value = "id") String id) {

		try {
			Optional<Planet> planet = planetService.findPlanetByID(id);

			if (planet.isEmpty()) {

				HashMap<String, String> body = new HashMap<>();
				body.put(Constants.MESSAGE, Constants.PLANET_NOT_FOUND);

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
			}

			return ResponseEntity.ok().body(planet);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Delete Planet.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Deleted Successfully."),
			@ApiResponse(code = 404, message = "The planet was not found.") })
	public ResponseEntity<?> deletePlanetById(@PathVariable(value = "id", required = true) String id) {

		try {

			Boolean isDeleted = planetService.deletePlanById(id);

			if (!isDeleted) {

				HashMap<String, String> body = new HashMap<>();
				body.put(Constants.MESSAGE, Constants.PLANET_NOT_FOUND);

				var responseIfWasNotDeleted = ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);

				return responseIfWasNotDeleted;
			}

			HashMap<String, String> body = new HashMap<>();
			body.put(Constants.MESSAGE, Constants.PLANET_DELETED);

			return ResponseEntity.ok().body(body);

		}

		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

}
