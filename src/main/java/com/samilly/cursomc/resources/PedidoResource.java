package com.samilly.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.samilly.cursomc.domain.Pedido;
import com.samilly.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos") //esse value eh o endpoint do endereco do navegador
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET) //"GET" Para pegar/recuperar um dado, value para dizer que no endpoint tbm tera o id
	public ResponseEntity<Pedido> Listar(@PathVariable Integer id) { //A anotacao vem pra dizer que a variavel 'id' que estara no endpoint declarada na linha acima sera usada aqui
		// o tipo 'ResponseEntity' eh para armazenar toda informacao que vier da requisicao, e a ? eh pra dizer que eh de qualquer tipo
		
		
		Pedido obj = service.find(id);
		
		return ResponseEntity.ok().body(obj); //.ok diz que a operacao ocorreu com sucesso e a resposta tera como corpo o obj que eu coloquei 
	}

}
