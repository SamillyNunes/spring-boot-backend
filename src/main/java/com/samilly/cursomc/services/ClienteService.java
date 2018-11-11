package com.samilly.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samilly.cursomc.domain.Cliente;
import com.samilly.cursomc.repositories.ClienteRepository;
import com.samilly.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired // Essa anotacao serve para instanciar uma dependencia automaticamente
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+
				", Tipo: "+Cliente.class.getName())); //Se nao achar o id vai retornar uma excecao com a mensagem passada e eo id e o nome da categoria
	}
	
	
}
