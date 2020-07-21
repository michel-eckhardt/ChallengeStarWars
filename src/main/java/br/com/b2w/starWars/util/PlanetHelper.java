package br.com.b2w.starWars.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public final class PlanetHelper {
	
	

	public static Integer isListEmpty(List<?> list) {
		
		return list.isEmpty() || list == null? 0 : list.size();
	}
	
	
	
}
