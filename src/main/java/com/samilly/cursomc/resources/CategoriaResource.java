package com.samilly.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.samilly.cursomc.domain.Categoria;
import com.samilly.cursomc.dto.CategoriaDTO;
import com.samilly.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias") //esse value eh o endpoint do endereco do navegador
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET) //"GET" Para pegar/recuperar um dado, value para dizer que no endpoint tbm tera o id
	public ResponseEntity<Categoria> Listar(@PathVariable Integer id) { //A anotacao vem pra dizer que a variavel 'id' que estara no endpoint declarada na linha acima sera usada aqui
		// o tipo 'ResponseEntity' eh para armazenar toda informacao que vier da requisicao, e a ? eh pra dizer que eh de qualquer tipo
		
		
		Categoria obj = service.find(id);
		
		return ResponseEntity.ok().body(obj); //.ok diz que a operacao ocorreu com sucesso e a resposta tera como corpo o obj que eu coloquei 
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){ //Agora faz validacao, assim com a anotacao @Valid. RequestBody faz com que o Json seja convertido para elemento java
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}"). //pega a uri no novo recurso que foi inserido
				buildAndExpand(obj.getId()).toUri(); //from current request eh para pegar o endereco da requisicao (localhost/categoria/...)
		return ResponseEntity.created(uri).build(); //ja pega a uri 201 que eh a padrao para criacao
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() { 		
		
		List<Categoria> list = service.findAll();
		
		List<CategoriaDTO> listDTO = list.stream().map(obj-> new CategoriaDTO(obj)).collect(Collectors.toList()); //o stream percorre cada elemento da lista e o map faz uma operacao para cada eelemento. O collect(collector.toList) converte tudo pra uma lista
		
		return ResponseEntity.ok().body(listDTO); 
	}
	
	@RequestMapping(value="/page",method=RequestMethod.GET) //paginacao
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page",defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage",defaultValue="24") Integer linesPerPage, //24 porque eh multiplo de 2, entao da pra fazer um app responsivo que se adeque legal
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy, 
			@RequestParam(value="direction",defaultValue="ASC") String direction) { 		
		
		Page<Categoria> list = service.findPage(page,linesPerPage,orderBy,direction);
		
		Page<CategoriaDTO> listDTO = list.map(obj-> new CategoriaDTO(obj)); //o stream percorre cada elemento da lista e o map faz uma operacao para cada eelemento. O collect(collector.toList) converte tudo pra uma lista
		
		return ResponseEntity.ok().body(listDTO); 
	}

}
