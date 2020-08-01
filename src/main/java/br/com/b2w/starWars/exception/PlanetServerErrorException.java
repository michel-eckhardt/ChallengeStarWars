package br.com.b2w.starWars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class PlanetServerErrorException extends RuntimeException {

	private static final long serialVersionUID = -9085491594324184187L;

	public static enum MESSAGES {

		DELETE_ERROR("An error occurred while trying to delete the planet."),
		CLIENT_ERROR("An error occurred while trying to access the SWAPI."),
		FIND_ERROR("An error occurred while trying to find the planet."),
		CREATE_ERROR("An error occurred while trying to create the planet."), 
		ERROR("Internal error.");

		private String msg;

		MESSAGES(String string) {
			this.msg = string;
		}

		public String msg() {
			return msg;
		}
	}

	public PlanetServerErrorException() {
	}

	public PlanetServerErrorException(MESSAGES message) {
		super(message.msg());
	}

	public PlanetServerErrorException(String message) {
		super(message);
	}

}
