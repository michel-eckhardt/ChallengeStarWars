package br.com.b2w.starWars.gateway.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Response {

	@JsonProperty(value = "results")
	List<ResultResponse> results;
}
