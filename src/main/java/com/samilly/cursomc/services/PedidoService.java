package com.samilly.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samilly.cursomc.domain.Pedido;
import com.samilly.cursomc.repositories.PedidoRepository;
import com.samilly.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired // Essa anotacao serve para instanciar uma dependencia automaticamente
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+
				", Tipo: "+Pedido.class.getName())); //Se nao achar o id vai retornar uma excecao com a mensagem passada e eo id e o nome da categoria
	}
	
	
}
