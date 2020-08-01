package br.com.b2w.starWars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PlanetBadRequestException extends RuntimeException {

	private static final long serialVersionUID = -9085491594324184187L;

	public static enum MESSAGES {

		PLANET_EXIST("This planet has already been created.");

		private String msg;

		MESSAGES(String string) {
			this.msg = string;
		}

		public String msg() {
			return msg;
		}
	}

	public PlanetBadRequestException() {
	}

	public PlanetBadRequestException(MESSAGES message) {
		super(message.msg());
	}

	public PlanetBadRequestException(String message) {
		super(message);
	}

}
