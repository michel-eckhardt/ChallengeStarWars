package br.com.b2w.starWars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PlanetNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3612431727650405518L;

	public static enum MESSAGES {

		PLANET_NOT_FOUND("Planet not found.");

		private String msg;

		MESSAGES(String string) {
			this.msg = string;
		}

		public String msg() {
			return msg;
		}
	}

	public PlanetNotFoundException() {
	}

	public PlanetNotFoundException(MESSAGES message) {
		super(message.msg());
	}

	public PlanetNotFoundException(String message) {
		super(message);
	}

}
