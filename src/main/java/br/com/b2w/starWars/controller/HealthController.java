package br.com.b2w.starWars.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(path = "/health")
@Api(value = "/v1", tags = "Health Check.")
public class HealthController {
	
	@GetMapping
	public ResponseEntity<?> healthcheck() {
		Map<String, String> status = new HashMap<String, String>();
		status.put("Status", "UP");
		return ResponseEntity.ok(status);
	}

}
