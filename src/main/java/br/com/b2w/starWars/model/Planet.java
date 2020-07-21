package br.com.b2w.starWars.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Planet {
	
	@Id
	private String id;
	
	private String name;
	
	private String climate;
	
	private String terrain;
	
	private Integer movies;

}
