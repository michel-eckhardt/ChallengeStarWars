package br.com.b2w.starWars.resource;

import java.util.HashMap;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(path = "/v1")
@Api(value = "/v1", tags = "Star Wars operations")
public class PlanetController {
	
	@Autowired
	PlanetService planetService;
	
	@Autowired
	PlanetConverter planetConverter;

	@PostMapping("planet")
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

}
