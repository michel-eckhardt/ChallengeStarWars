package br.com.b2w.starWars.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.b2w.starWars.dto.PlanetConverter;
import br.com.b2w.starWars.dto.PlanetDTO;
import br.com.b2w.starWars.exception.PlanetBadRequestException;
import br.com.b2w.starWars.exception.PlanetNotFoundException;
import br.com.b2w.starWars.model.Planet;
import br.com.b2w.starWars.service.PlanetService;
import io.restassured.http.ContentType;

@WebMvcTest
public class PlanetControllerTest {

	@Autowired
	private PlanetController planetController;

	@MockBean
	PlanetService planetService;

	@MockBean
	PlanetConverter planetConverter;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		standaloneSetup(planetController);
	}

	@Test
	public void returnSuccess_findPlanetById() {

		when(this.planetService.findPlanetByID("1"))
				.thenReturn(new Planet("1", "Test Planet", "terrain", "cold", 4));

		given().accept(ContentType.JSON).when().get("/v1/planet/{id}", "1").then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void returnNotFound_findPlanetById() {

		when(this.planetService.findPlanetByID("2")).thenThrow(new PlanetNotFoundException(PlanetNotFoundException.MESSAGES.PLANET_NOT_FOUND));

		given().accept(ContentType.JSON).when().get("/v1/planet/{id}", "2").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void returnSuccess_findAllPlanet() {

		when(this.planetService.getAllPlanets()).thenReturn(new ArrayList<Planet>());

		given().accept(ContentType.JSON).when().get("/v1/planet").then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void returnSuccess_deletePlanet() {
		given().accept(ContentType.JSON).when().delete("/v1/planet/{id}", "3").then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void returnSuccess_createPlanet() throws Exception {
		
		PlanetDTO dto = new PlanetDTO("Planet Test","teste","teste",1);
		
		when(this.planetService.savePlanet(dto))
		.thenReturn(new Planet("5","Planet Test","teste","teste",1));
		
		var payload = objectMapper.writeValueAsString(dto);
	    mockMvc.perform(
	    		post("/v1/planet").contentType(MediaType.APPLICATION_JSON)
	            .content(payload))
	            .andExpect(status().isCreated());
	}

	@Test
	public void returnBadRequest_createPlanet() throws Exception {
		
		PlanetDTO dto = new PlanetDTO("Planet Test","teste","teste",1);
		
		when(this.planetService.savePlanet(dto))
		.thenThrow(new PlanetBadRequestException(PlanetBadRequestException.MESSAGES.PLANET_EXIST));
		var payload = objectMapper.writeValueAsString(dto);
	    mockMvc.perform(
	    		post("/v1/planet").contentType(MediaType.APPLICATION_JSON)
	            .content(payload))
	            .andExpect(status().isBadRequest());
		
	}

}