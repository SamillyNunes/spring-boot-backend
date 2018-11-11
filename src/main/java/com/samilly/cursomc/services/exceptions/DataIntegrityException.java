package com.samilly.cursomc.services.exceptions;

public class DataIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String msg) { //construtor que reaproveita a mensagem da classe pai 
		super(msg); 
	}
	
	public DataIntegrityException(String msg, Throwable cause) { //agora com uma possivel causa que aconteceu antes (?)
		super(msg,cause);
	}

}
