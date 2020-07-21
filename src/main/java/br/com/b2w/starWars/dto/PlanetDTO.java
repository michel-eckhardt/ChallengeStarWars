package br.com.b2w.starWars.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PlanetDTO {
	
	@NotNull
	private String name;
	@NotNull
	private String climate;
	@NotNull
	private String terrains;
	@NotNull
	private Integer movies;
	
}
