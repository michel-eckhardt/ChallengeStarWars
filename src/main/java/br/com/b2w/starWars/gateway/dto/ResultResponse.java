package br.com.b2w.starWars.gateway.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResultResponse {
	
	private String name;
	
	private String climate;
	
	private String terrain;
	
	private List<String> films;

}
