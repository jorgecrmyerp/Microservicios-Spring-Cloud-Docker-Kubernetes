package com.jgr.micro.cursos.error;

public class IdNoEncontradoException extends RuntimeException {

	public IdNoEncontradoException(String id) {
		super("Usuario con ID: ".concat(id).concat(" no existe en el sistema"));
	}

	private static final long serialVersionUID = 1L;

}
