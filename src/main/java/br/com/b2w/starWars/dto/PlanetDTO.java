package br.com.b2w.starWars.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanetDTO {
	
	@NotNull
	private String name;
	@NotNull
	private String climate;
	@NotNull
	private String terrain;
	
	private Integer movies;
	
}
