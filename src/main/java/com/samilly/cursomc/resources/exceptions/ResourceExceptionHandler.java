package com.samilly.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.samilly.cursomc.services.exceptions.DataIntegrityException;
import com.samilly.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice//anotacao para classes que tratam de excecoes que sao capturas em classes controllers
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class) //diz que eh um tratador de excecoes desse tipo de excecao passado
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){ //padrao do Controller advice 
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(),e.getMessage(),System.currentTimeMillis()); //Passa o status de nao encontrado, a mensagem da classe objNotFoundExc, e o tempo do sistema
	
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class) //diz que eh um tratador de excecoes desse tipo de excecao passado
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){ //padrao do Controller advice 
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage(),System.currentTimeMillis()); //Passa o status de nao encontrado, a mensagem da classe objNotFoundExc, e o tempo do sistema
	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	
}
