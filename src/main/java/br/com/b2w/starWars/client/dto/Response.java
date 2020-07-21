package br.com.b2w.starWars.client.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Response {

	Integer count;

	String next;

	String previous;

	@JsonProperty(value = "results")
	List<ResultResponse> results;
}
